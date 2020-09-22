/**
 * Copyright (c) 2020-present, Dash Core Team
 *
 * This source code is licensed under the MIT license found in the
 * COPYING file in the root directory of this source tree.
 */

package org.dashevo.dpp.contract
import org.dashevo.dpp.Fixtures
import org.dashevo.dpp.toHexString
import org.dashevo.dpp.util.Cbor
import org.dashevo.dpp.util.HashUtils
import org.dashevo.dpp.util.HashUtils.fromHex
import org.dashevo.dpp.util.HashUtilsTest
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

class DataContractTest {

    companion object {
        val factory = ContractFactory()
    }

    @Test
    fun testContract() {
        var contract = Fixtures.getDataContractFixtures()

        assertEquals(DataContract.SCHEMA, contract.schema)
        assertEquals(3, contract.documents.size)
        assertEquals("3eHkM3mWjutxbc3EQwnVQHyymDgbZfK4EhZRectK11jV", contract.id)
    }

    /*@Test
    fun testContactFactory() {
        var factory = ContractFactory()

        val json = File("src/test/resources/data/documentsforcontract.json").readText()//"{\r\n\"name\" : \"abc\" ,\r\n\"email id \" : [\"abc@gmail.com\",\"def@gmail.com\",\"ghi@gmail.com\"]\r\n}"
        val jsonObject = JSONObject(json)
        val map = jsonObject.toMap()

        val rawContract = HashMap<String, Any?>()
        rawContract["documents"] = map
        rawContract["contractId"] = "9rjz23TQ3rA2agxXD56XeDfw63hHJUwuj7joxSBEfRgX"
        rawContract["\$schema"] = DataContract.SCHEMA
        rawContract["definitions"] = JSONObject("{lastName: { type: 'string', }, }").toMap()

        val factoryCreatedContract = factory.createDataContract(rawContract)
        val fixtureCreatedContract = Fixtures.getDataContractFixtures()

        assertEquals(fixtureCreatedContract.id, factoryCreatedContract.id)

        val anotherFactoryCreatedContract = factory.createDataContract("9rjz23TQ3rA2agxXD56XeDfw63hHJUwuj7joxSBEfRgX", map)

        assertEquals(fixtureCreatedContract.id, anotherFactoryCreatedContract.id)
    }*/

    @Test
    fun applyStateTransition() {
        val dataContract = Fixtures.getDataContractFixtures()
        val stateTransition = ContractStateTransition(dataContract)

        val result =  factory.createStateTransition(dataContract)

        assertEquals(result.toJSON(), stateTransition.toJSON())
    }

    @Test
    fun dashPayContractTest() {
        val jsonDashPay = File("src/test/resources/data/dashpay-contract.json").readText()
        val jsonObject = JSONObject(jsonDashPay)
        val rawContract = jsonObject.toMap()

        val serializedHexData = "a563246964782c465a707538744b37626979526451664364627359743137674a6d764c4279635938544a5851526f63546f70686724736368656d61783468747470733a2f2f736368656d612e646173682e6f72672f6470702d302d342d302f6d6574612f646174612d636f6e7472616374676f776e65724964782c336d3362793467756b4732676d507578337443513676434d64427167355875324c3675715650416b4165313269646f63756d656e7473a36770726f66696c65a467696e646963657382a266756e69717565f56a70726f7065727469657381a168246f776e6572496463617363a16a70726f7065727469657382a168246f776e6572496463617363a16a2475706461746564417463617363687265717569726564826a246372656174656441746a247570646174656441746a70726f70657274696573a36961766174617255726ca3647479706566737472696e6766666f726d61746375726c696d61784c656e6774681908006b646973706c61794e616d65a2647479706566737472696e67696d61784c656e677468146d7075626c69634d657373616765a2647479706566737472696e67696d61784c656e677468188c746164646974696f6e616c50726f70657274696573f46b636f6e74616374496e666fa467696e646963657382a266756e69717565f56a70726f7065727469657383a168246f776e6572496463617363a176726f6f74456e6372797074696f6e4b6579496e64657863617363a1781c64657269766174696f6e456e6372797074696f6e4b6579496e64657863617363a16a70726f7065727469657382a168246f776e6572496463617363a16a2475706461746564417463617363687265717569726564866a246372656174656441746a247570646174656441746b656e63546f5573657249646b707269766174654461746176726f6f74456e6372797074696f6e4b6579496e646578781c64657269766174696f6e456e6372797074696f6e4b6579496e6465786a70726f70657274696573a46b656e63546f557365724964a5647479706566737472696e67677061747465726e725e285b412d5a612d7a302d392b2f5d292a24696d61784c656e677468182b696d696e4c656e677468182b6f636f6e74656e74456e636f64696e67666261736536346b7072697661746544617461a6647479706566737472696e67677061747465726e725e285b412d5a612d7a302d392b2f5d292a24696d61784c656e677468190aab696d696e4c656e67746818406b6465736372697074696f6e785c546869732069732074686520656e637279707465642076616c756573206f6620616c6961734e616d65202b206e6f7465202b20646973706c617948696464656e20656e636f64656420617320616e20617272617920696e2063626f726f636f6e74656e74456e636f64696e676662617365363476726f6f74456e6372797074696f6e4b6579496e646578a1647479706567696e7465676572781c64657269766174696f6e456e6372797074696f6e4b6579496e646578a1647479706567696e7465676572746164646974696f6e616c50726f70657274696573f46e636f6e7461637452657175657374a467696e646963657384a266756e69717565f56a70726f7065727469657383a168246f776e6572496463617363a168746f55736572496463617363a1706163636f756e745265666572656e636563617363a16a70726f7065727469657382a168246f776e6572496463617363a168746f55736572496463617363a16a70726f7065727469657382a168746f55736572496463617363a16a2463726561746564417463617363a16a70726f7065727469657382a168246f776e6572496463617363a16a2463726561746564417463617363687265717569726564866a2463726561746564417468746f55736572496472656e637279707465645075626c69634b65796e73656e6465724b6579496e64657871726563697069656e744b6579496e646578706163636f756e745265666572656e63656a70726f70657274696573a668746f557365724964a5647479706566737472696e67677061747465726e725e285b412d5a612d7a302d392b2f5d292a24696d61784c656e677468182b696d696e4c656e677468182b6f636f6e74656e74456e636f64696e67666261736536346e73656e6465724b6579496e646578a1647479706567696e7465676572706163636f756e745265666572656e6365a1647479706567696e746567657271726563697069656e744b6579496e646578a1647479706567696e746567657272656e637279707465645075626c69634b6579a5647479706566737472696e67677061747465726e725e285b412d5a612d7a302d392b2f5d292a24696d61784c656e6774681880696d696e4c656e67746818806f636f6e74656e74456e636f64696e676662617365363475656e637279707465644163636f756e744c6162656ca5647479706566737472696e67677061747465726e725e285b412d5a612d7a302d392b2f5d292a24696d61784c656e677468186b696d696e4c656e67746818406f636f6e74656e74456e636f64696e6766626173653634746164646974696f6e616c50726f70657274696573f46f70726f746f636f6c56657273696f6e00"
        val serializedData = HashUtils.fromHex(serializedHexData)
        val fromRaw = factory.createFromObject(rawContract)

        val fromSerialized = factory.createFromSerialized(serializedData)
        val fromRoundTrip = factory.createFromSerialized(fromRaw.serialize())
        assertEquals(fromRaw.toJSON(), fromRoundTrip.toJSON())
        assertEquals(fromRaw.toJSON(), fromSerialized.toJSON())
        assertEquals(fromRaw.serialize().toHexString(), fromRoundTrip.serialize().toHexString())
        assertEquals(fromRaw.serialize().toHexString(), fromSerialized.serialize().toHexString())
    }

    @Test
    fun contractFromCborTest() {
        val contractBytes = "a563246964782c35363676634a6b6d65625643416232446b6a3279564d536747466373736875706e517174737a3152466263796724736368656d61783468747470733a2f2f736368656d612e646173682e6f72672f6470702d302d342d302f6d6574612f646174612d636f6e7472616374676f776e65724964782c374a666343353269434c47693965755861374e673372654b446a4171616f575a4e6a447641327a4542436e6b69646f63756d656e7473a266646f6d61696ea567696e646963657382a266756e69717565f56a70726f7065727469657382a1781a6e6f726d616c697a6564506172656e74446f6d61696e4e616d6563617363a16f6e6f726d616c697a65644c6162656c63617363a266756e69717565f56a70726f7065727469657381a1781c7265636f7264732e64617368556e697175654964656e746974794964636173636824636f6d6d656e74790135496e206f7264657220746f20726567697374657220646f6d61696e20796f75206e65656420746f206372656174652061207072656f726465722e20546865207072656f726465722073746570206973206e656564656420746f2070726576656e74206d616e2d696e2d7468652d6d6964646c652061747461636b732e206e6f726d616c697a65644c6162656c202b20272e27202b206e6f726d616c697a6564506172656e74446f6d61696e206d757374206e6f74206265206c6f6e676572207468616e20323533206368617273206c656e67746820617320646566696e65642062792052464320313033352e20446f6d61696e20646f63756d656e74732061726520696d6d757461626c653a206d6f64696669636174696f6e20616e642064656c6574696f6e20617265207265737472696374656468726571756972656486656c6162656c6f6e6f726d616c697a65644c6162656c781a6e6f726d616c697a6564506172656e74446f6d61696e4e616d656c7072656f7264657253616c74677265636f7264736e737562646f6d61696e52756c65736a70726f70657274696573a6656c6162656ca5647479706566737472696e67677061747465726e78265e28283f212d295b612d7a412d5a302d392d5d7b302c36327d5b612d7a412d5a302d395d2924696d61784c656e677468183f696d696e4c656e677468036b6465736372697074696f6e7819446f6d61696e206c6162656c2e20652e672e2027426f62272e677265636f726473a66474797065666f626a6563746824636f6d6d656e74788a436f6e73747261696e742077697468206d617820616e64206d696e2070726f7065727469657320656e737572652074686174206f6e6c79206f6e65206964656e74697479207265636f7264206973207573656420656974686572206064617368556e697175654964656e74697479496460206f72206064617368416c6961734964656e746974794964606a70726f70657274696573a27364617368416c6961734964656e746974794964a6647479706566737472696e67677061747465726e783f5e5b31323334353637383941424344454647484a4b4c4d4e505152535455565758595a6162636465666768696a6b6d6e6f707172737475767778797a5d2b246824636f6d6d656e74782553686f756c6420626520657175616c20746f2074686520646f63756d656e74206f776e6572696d61784c656e677468182c696d696e4c656e677468182a6b6465736372697074696f6e785c4964656e7469747920494420737472696e672073686f756c64206265207573656420746f20637265617465206d756c7469706c6520616c696173206e616d657320666f72204964656e746974792e2062617365353820737472696e677464617368556e697175654964656e746974794964a6647479706566737472696e67677061747465726e783f5e5b31323334353637383941424344454647484a4b4c4d4e505152535455565758595a6162636465666768696a6b6d6e6f707172737475767778797a5d2b246824636f6d6d656e74782553686f756c6420626520657175616c20746f2074686520646f63756d656e74206f776e6572696d61784c656e677468182c696d696e4c656e677468182a6b6465736372697074696f6e78564964656e7469747920494420737472696e672073686f756c64206265207573656420746f206372656174652061207072696d617279206e616d6520666f72204964656e746974792e2062617365353820737472696e676d6d617850726f70657274696573016d6d696e50726f7065727469657301746164646974696f6e616c50726f70657274696573f46c7072656f7264657253616c74a6647479706566737472696e67677061747465726e725e285b412d5a612d7a302d392b2f5d292a24696d61784c656e677468182b696d696e4c656e677468182b6b6465736372697074696f6e7826446f6d61696e207072652d6f726465722073616c742e2033322072616e646f6d2062797465736f636f6e74656e74456e636f64696e67666261736536346e737562646f6d61696e52756c6573a56474797065666f626a656374687265717569726564816f616c6c6f77537562646f6d61696e736a70726f70657274696573a16f616c6c6f77537562646f6d61696e73a2647479706567626f6f6c65616e6b6465736372697074696f6e782d54686973206f7074696f6e20646566696e65732077686f2063616e2063726561746520737562646f6d61696e736b6465736372697074696f6e7835537562646f6d61696e2072756c657320616c6c6f777320746f20646566696e652072756c657320666f7220737562646f6d61696e73746164646974696f6e616c50726f70657274696573f46f6e6f726d616c697a65644c6162656ca5647479706566737472696e67677061747465726e78205e28283f212d295b612d7a302d392d5d7b302c36327d5b612d7a302d395d29246824636f6d6d656e747865546869732070726f70657274792077696c6c20626520646570726563617465642064756520746f206361736520696e73656e73697469766520696e64696365732e204d75737420626520657175616c20746f206c6162656c20696e206c6f77657263617365696d61784c656e677468183f6b6465736372697074696f6e7853446f6d61696e206c6162656c20696e2061206c6f776572206361736520666f72206361736520696e73656e73697469766520756e697175656e6573732076616c69646174696f6e2e20652e672e2027626f6227781a6e6f726d616c697a6564506172656e74446f6d61696e4e616d65a6647479706566737472696e67677061747465726e78265e247c5e28283f212d295b612d7a302d392d5c2e5d7b302c3138397d5b612d7a302d395d29246824636f6d6d656e74788f4d75737420626520657175616c20746f206578697374696e6720646f6d61696e206f722063616e20626520656d70747920696620796f752077616e7420746f20637265617465206120746f70206c6576656c20646f6d61696e2e204f6e6c792074686520636f6e7472616374206f776e65722063616e2063726561746520746f70206c6576656c20646f6d61696e73696d61784c656e67746818be696d696e4c656e677468006b6465736372697074696f6e785f412066756c6c20706172656e7420646f6d61696e206e616d6520696e206c6f776572206361736520666f72206361736520696e73656e73697469766520756e697175656e6573732076616c69646174696f6e2e20652e672e20276461736827746164646974696f6e616c50726f70657274696573f4687072656f72646572a567696e646963657381a266756e69717565f56a70726f7065727469657381a17073616c746564446f6d61696e48617368636173636824636f6d6d656e74784a5072656f7264657220646f63756d656e74732061726520696d6d757461626c653a206d6f64696669636174696f6e20616e642064656c6574696f6e206172652072657374726963746564687265717569726564817073616c746564446f6d61696e486173686a70726f70657274696573a17073616c746564446f6d61696e48617368a6647479706566737472696e67677061747465726e725e285b412d5a612d7a302d392b2f5d292a24696d61784c656e677468182b696d696e4c656e677468182b6b6465736372697074696f6e7877446f75626c65207368612d323536206f6620636f6e636174656e617465642073616c742c20606e6f726d616c697a65644c6162656c6020616e6420606e6f726d616c697a6564506172656e74446f6d61696e4e616d65602e2053616c742073686f756c642062652033322072616e646f6d2062797465736f636f6e74656e74456e636f64696e6766626173653634746164646974696f6e616c50726f70657274696573f46f70726f746f636f6c56657273696f6e00"

        val contract = factory.createFromSerialized(fromHex(contractBytes))
        val jsonDashPay = File("src/test/resources/data/dpns-contract.json").readText()
        val jsonObject = JSONObject(jsonDashPay)
        val rawContract = jsonObject.toMap()

        val contractFromJson = DataContract(rawContract)
        assertEquals(contract.toJSON(), contractFromJson.toJSON())
    }

    @Test
    fun verifyLoadingContractFromFile() {
        val dataContractST = Fixtures.getDataContractSTSignedFixture()
        val dataContractSTTwo = Fixtures.getDataContractSTSignedFixtureTwo()
        assertEquals("EzLBmQdQXYMaoeXWNaegK18iaaCDShitN3s14US3DunM", dataContractST.dataContract.id)
        assertEquals(dataContractST.signature, dataContractSTTwo.signature)
    }

//    @Test
//    fun verifySignedDataContractSTTest() {
//        val dataContractST = Fixtures.getDataContractSTSignedFixture()
//        var identity = Fixtures.getIdentityForSignaturesFixture()
//
//        assertTrue(dataContractST.verifySignature(identity.publicKeys[0]))
//
//        val dataContractSTTwo = Fixtures.getDataContractSTSignedFixtureTwo();
//        assertEquals(dataContractST.toJSON(), dataContractSTTwo.toJSON())
//    }
}
