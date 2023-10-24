package com.example.cartoon

val Any?.isNull get() = this == null

/*
Usage
if (obj.isNull) {
    // Run if object is null
} else {
    // Run if object is not null
}
*/

fun Any?.ifNull(block: () -> Unit) = run {
    if (this == null) {
        block()
    }
}

/*
Usage
obj.ifNull {
    // Write code
}
*/

inline fun <T : Any, R> T?.withNotNull(block: (T) -> R): R? {
    return this?.let(block)
}

/*
Usage
val nullableValue: String? = null
nullableValue.withNotNull { value ->
    // Code here will only be executed if nullableValue is not null
}*/