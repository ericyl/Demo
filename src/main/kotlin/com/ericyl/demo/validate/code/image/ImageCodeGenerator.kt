package com.ericyl.demo.validate.code.image

import com.ericyl.demo.properties.DemoProperties
import com.ericyl.demo.validate.code.ValidateCodeGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.bind.ServletRequestUtils
import org.springframework.web.context.request.ServletWebRequest
import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage
import java.util.*

@Component
class ImageCodeGenerator : ValidateCodeGenerator<ImageCode> {


    @Autowired
    private val properties: DemoProperties? = null

    override fun generator(request: ServletWebRequest): ImageCode {

        val width = ServletRequestUtils.getIntParameter(request.request, "width", properties!!.validateCode!!.image!!.width!!)
        val height = ServletRequestUtils.getIntParameter(request.request, "height", properties.validateCode!!.image!!.height!!)
        val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)

        val g = image.graphics

        val random = Random()

        g.color = getRandColor(200, 250)
        g.fillRect(0, 0, width, height)
        g.font = Font("Times New Roman", Font.ITALIC, 20)
        g.color = getRandColor(160, 200)
        for (i in 0..154) {
            val x = random.nextInt(width)
            val y = random.nextInt(height)
            val xl = random.nextInt(12)
            val yl = random.nextInt(12)
            g.drawLine(x, y, x + xl, y + yl)
        }

        val sRand = StringBuilder()
        for (i in 0 until (properties.validateCode?.image?.count ?: 4)) {
            val rand = random.nextInt(10).toString()
            sRand.append(rand)
            g.color = Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110))
            g.drawString(rand, 13 * i + 6, 16)
        }

        g.dispose()

        return ImageCode(sRand.toString(), properties.validateCode!!.image!!.expiredSeconds!!, image)
    }

    /**
     * 生成随机背景条纹
     *
     * @param fc
     * @param bc
     * @return
     */
    private fun getRandColor(fc: Int, bc: Int): Color {
        var fc = fc
        var bc = bc
        val random = Random()
        if (fc > 255) {
            fc = 255
        }
        if (bc > 255) {
            bc = 255
        }
        val r = fc + random.nextInt(bc - fc)
        val g = fc + random.nextInt(bc - fc)
        val b = fc + random.nextInt(bc - fc)
        return Color(r, g, b)
    }
}
