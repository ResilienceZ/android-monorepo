package dev.ricoferdian.resiliencez.prayitna.service

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TestGeoHashingService {
    private lateinit var service: GeoHashingService
    @Before
    fun setup(){
        service = GeoHashingServiceImpl()
    }

    @Test
    fun testEncode() {
        Assert.assertEquals("qqguwxmd2spp", service.encode(-6.200000, 106.816666))
        Assert.assertEquals("u4pruydqqvj8", service.encode(57.64911, 10.40744))
        Assert.assertEquals("gcwckyb77b2f", service.encode(53.694865, -1.548567))
    }

    @Test
    fun testEncodeWithDifferentPrecision() {
        Assert.assertEquals("qqguwxmd2sp", service.encode(-6.200000, 106.816666, 11))
        Assert.assertEquals("u4pruydqqvj", service.encode(57.64911, 10.40744, 11))
        Assert.assertEquals("gcwckyb77b2", service.encode(53.694865, -1.548567, 11))
    }
}