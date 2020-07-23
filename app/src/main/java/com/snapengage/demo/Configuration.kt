package com.snapengage.demo

import com.chat.sdk.Chat

fun Chat.Configuration.debug() =
    "Provider:\n$provider\n\n" +
            "Js url:\n$jsUrl\n\n" +
            "Custom Js Variables:\n$customVariables"