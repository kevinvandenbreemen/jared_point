package com.jared.point.registry

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class RegistryTest {
    @Test
    fun `should not register the same item twice`() {
        val registry = Registry()
        registry.add(
            RegistryItem(
            "test", 123
            )
        )
        registry.add(RegistryItem(
            "test", 123
        ))

        registry.items.size shouldBeEqualTo 1
        registry.items[0] shouldBeEqualTo RegistryItem(
            "test", 123
        )
    }

    @Test
    fun `should return flag indicating success`() {
        val registry = Registry()
        registry.add(
            RegistryItem(
                "test", 123
            )
        ) shouldBeEqualTo true

        registry.add(
            RegistryItem(
                "test", 123
            )
        ) shouldBeEqualTo false
    }
}