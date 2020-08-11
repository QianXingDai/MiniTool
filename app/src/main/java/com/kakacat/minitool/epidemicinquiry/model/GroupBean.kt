package com.kakacat.minitool.epidemicinquiry.model

import java.util.*

class GroupBean {
    var location: String? = null
    var currentConfirmCount: String? = null
    var totalConfirmCount: String? = null
    var susPectCount: String? = null
    var curedCount: String? = null
    var deadCount: String? = null
    var childBeanList: MutableList<ChildBean>? = null
        get() {
            if (field == null) {
                field = ArrayList()
            }
            return field
        }
}