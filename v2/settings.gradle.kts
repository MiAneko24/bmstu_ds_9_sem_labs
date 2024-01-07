rootProject.name = "hotelReservation"

pluginManagement {
    plugins {
        id("org.jetbrains.kotlin.jvm") version "1.9.10"
    }
}

include(
    "reservationService",
    "paymentService",
    "loyaltyService",
    "gateway",
)
