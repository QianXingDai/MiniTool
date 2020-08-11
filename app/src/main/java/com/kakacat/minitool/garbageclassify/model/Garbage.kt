package com.kakacat.minitool.garbageclassify.model

class Garbage {
    /*
    name	string	智能眼镜	废弃物名称
    type	int	0	垃圾分类，0为可回收、1为有害、2为厨余(湿)、3为其他(干)
    aipre	int	0	智能预判，0为正常结果，1为预判结果
    explain	string	适宜回收、可循环利用的生活废弃物	分类解释
    contain	string	各类废金属、玻璃瓶、易拉罐、饮料瓶......	包含类型
    tip	string	轻投轻放；清洁干燥，避免污染，费纸尽量平整......	投放提示*/
    var name: String? = null
    var type = 0
    var aipre = 0
    var explain: String? = null
    var contain: String? = null
    var tip: String? = null

}