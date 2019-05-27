package com.ericyl.demo.validate.code.image

import com.ericyl.demo.validate.code.ValidateCode
import lombok.Data

import java.awt.image.BufferedImage

@Data
class ImageCode(code: String, expiredSeconds: Int, val image: BufferedImage) : ValidateCode(code, expiredSeconds)
