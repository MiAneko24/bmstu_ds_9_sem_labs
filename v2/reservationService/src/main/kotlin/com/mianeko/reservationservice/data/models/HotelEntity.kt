package com.mianeko.reservationservice.data.models

import org.ktorm.entity.Entity
import java.util.UUID

interface HotelEntity : Entity<HotelEntity> {
    companion object : Entity.Factory<HotelEntity>()
    var id: Int
    var hotelUid: UUID
    var name: String
    var country: String
    var city: String
    var address: String
    var stars: Int?
    var price: Int
}
