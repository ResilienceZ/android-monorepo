package dev.ricoferdian.resiliencez.prayitna.service

class GeoHashingServiceImpl : GeoHashingService {
    private val base32 = "0123456789bcdefghjkmnpqrstuvwxyz".toCharArray()

    override fun encode(latitude: Double, longitude: Double, precision: Int): String {
        val latInterval = doubleArrayOf(-90.0, 90.0)
        val lonInterval = doubleArrayOf(-180.0, 180.0)
        val geohash = StringBuilder()
        var isEvenBit = true
        var bit = 0
        var ch = 0

        while (geohash.length < precision) {
            if (isEvenBit) {
                val mid = (lonInterval[0] + lonInterval[1]) / 2
                if (longitude > mid) {
                    ch = ch or (1 shl (4 - bit))
                    lonInterval[0] = mid
                } else {
                    lonInterval[1] = mid
                }
            } else {
                val mid = (latInterval[0] + latInterval[1]) / 2
                if (latitude > mid) {
                    ch = ch or (1 shl (4 - bit))
                    latInterval[0] = mid
                } else {
                    latInterval[1] = mid
                }
            }
            isEvenBit = !isEvenBit
            if (bit < 4) {
                bit++
            } else {
                geohash.append(base32[ch])
                bit = 0
                ch = 0
            }
        }

        return geohash.toString()
    }
}