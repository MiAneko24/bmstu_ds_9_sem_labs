package com.mianeko.loyaltyservice.data

import com.mianeko.common.loyalty.Loyalty
import com.mianeko.common.loyalty.LoyaltyLevel
import com.mianeko.loyaltyservice.data.exceptions.IncorrectLoyaltyDecrement
import com.mianeko.loyaltyservice.data.models.LoyaltyEntity
import com.mianeko.loyaltyservice.data.models.loyalties
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import org.ktorm.entity.toList
import org.springframework.stereotype.Repository

interface LoyaltyRepository {
    fun getAll(): List<Loyalty>
    fun getForUser(username: String): Loyalty
    fun updateReservations(username: String, inc: Boolean): Loyalty
}

@Repository
class LoyaltyRepositoryImpl(
    private val db: Database,
): LoyaltyRepository {
    private fun mapper(entity: LoyaltyEntity): Loyalty {
        return Loyalty(entity.id, entity.username, entity.reservationCount, entity.status, entity.discount)
    }

    override fun getAll(): List<Loyalty> {
        return db.loyalties.toList().map(::mapper)
    }

    override fun getForUser(username: String): Loyalty {
        var loyalty = db.loyalties.find { it.username eq username }
        if (loyalty == null) {
            loyalty = getNewLoyalty(username)
            db.loyalties.add(loyalty)
        }
        return mapper(loyalty)
    }

    override fun updateReservations(username: String, inc: Boolean): Loyalty {
        val currentLoyalty = db.loyalties.find { it.username eq username }
        if (!inc && (currentLoyalty == null || currentLoyalty.reservationCount == 0)) {
            throw IncorrectLoyaltyDecrement(username)
        }
        val loyalty = currentLoyalty ?: getNewLoyalty(username)
        loyalty.reservationCount += if (inc) 1 else -1
        loyalty.status = getCurrentLoyaltyLevel(loyalty.reservationCount)
        loyalty.discount = getDiscountByLoyaltyLevel(loyalty.status)

        db.loyalties.add(loyalty)
        return mapper(loyalty)
    }

    private fun getCurrentLoyaltyLevel(reservationCount: Int): LoyaltyLevel {
        return when {
            reservationCount <= 10 -> LoyaltyLevel.BRONZE
            reservationCount <= 20 -> LoyaltyLevel.SILVER
            else -> LoyaltyLevel.GOLD
        }
    }

    private fun getDiscountByLoyaltyLevel(loyaltyLevel: LoyaltyLevel): Int {
        return when (loyaltyLevel) {
            LoyaltyLevel.BRONZE -> 5
            LoyaltyLevel.SILVER -> 7
            LoyaltyLevel.GOLD -> 10
        }
    }

    private fun getNewLoyalty(username: String) = LoyaltyEntity {
        this.username = username
        this.reservationCount = 0
        this.discount = 0
    }
}
