package ru.yoomoney.tech.grafana.dsl.panels.gauge

import org.json.JSONObject
import ru.yoomoney.tech.grafana.dsl.json.Json
import ru.yoomoney.tech.grafana.dsl.json.jsonObject

/**
 * Options to refine visualization
 * @author X Y
 * @since XX.YY.2021
 */
class GaugePanelDisplayOptions(
    private val reduceOptions: GaugePanelReduceOptions = GaugePanelReduceOptions(),
    private val showThresholdLabels: Boolean = false,
    private val showThresholdMarkers: Boolean = true
) : Json<JSONObject> {
    override fun toJson(): JSONObject = jsonObject {
        "reduceOptions" to reduceOptions
        "showThresholdLabels" to showThresholdLabels
        "showThresholdMarkers" to showThresholdMarkers
    }
}

/**
 * Builder for GaugePanelDisplayOptions
 * @author X Y
 * @since XX.YY.2021
 */
class GaugePanelDisplayOptionsBuilder {
    private var reduceOptions: GaugePanelReduceOptions = GaugePanelReduceOptions()
    var showThresholdLabels: Boolean = false
    var showThresholdMarkers: Boolean = false
    fun reduceOptions(build: GaugePanelReduceOptionsBuilder.() -> Unit) {
        val builder = GaugePanelReduceOptionsBuilder()
        builder.build()
        reduceOptions = builder.createReduceOptions()
    }

    fun createGaugePanelDisplayOptions() = GaugePanelDisplayOptions(reduceOptions = reduceOptions)
}

/**
 * @author X Y
 * @since XX.YY.2021
 */
class GaugePanelReduceOptions(
    private val values: Boolean = false,
    private val calcs: List<String> = listOf("lastNotNull"),
    private val fields: String = ""
) : Json<JSONObject> {
    override fun toJson(): JSONObject = jsonObject {
        "values" to values
        "calcs" to calcs
        "fields" to fields
    }
}

class GaugePanelReduceOptionsBuilder {
    var fields: String = ""
    var values: Boolean = false
    var calcs: List<String> = listOf("lastNotNull")
    fun createReduceOptions(): GaugePanelReduceOptions {
        return GaugePanelReduceOptions(fields = fields, values = values, calcs = calcs)
    }
}

