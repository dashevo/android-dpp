package org.dashevo.dpp.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HashUtilsTest {

    @Test
    fun generateDataContractIdTest() {
        val ownerId = "23wdhodag"
        val entropy = "5dz916pTe1"

        assertEquals("CnS7cz4z1qoPsNfEgpgyVnKdtH2u7bgzZXHLcCQt24US", HashUtils.generateDataContractId(ownerId, entropy))
    }

    @Test
    fun generateDocumentIdTest() {
        val ownerId = "23wdhodag"
        val entropy = "5dz916pTe1"

        assertEquals("CnS7cz4z1qoPsNfEgpgyVnKdtH2u7bgzZXHLcCQt24US", HashUtils.generateDataContractId(ownerId, entropy))
    }
}