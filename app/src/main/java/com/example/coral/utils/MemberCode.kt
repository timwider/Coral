package com.example.coral.utils


// Состояние мембера при добавлении.

enum class MemberCode {
    COMPLETE, // name, phone, daysLeft, payments
    NO_PHONE,
    NO_PAYMENT,
    INCOMPLETE // name, daysLeft
}