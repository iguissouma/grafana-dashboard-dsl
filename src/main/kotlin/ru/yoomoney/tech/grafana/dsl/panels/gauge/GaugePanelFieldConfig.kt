package ru.yoomoney.tech.grafana.dsl.panels.gauge

import org.json.JSONObject
import ru.yoomoney.tech.grafana.dsl.json.Json
import ru.yoomoney.tech.grafana.dsl.json.JsonArray
import ru.yoomoney.tech.grafana.dsl.json.jsonObject
import ru.yoomoney.tech.grafana.dsl.panels.NullValue
import ru.yoomoney.tech.grafana.dsl.panels.ThresholdMode
import ru.yoomoney.tech.grafana.dsl.panels.Thresholds
import ru.yoomoney.tech.grafana.dsl.panels.ThresholdsBuilder

/**
 * Used to change how the data is displayed in visualizations
 * @author X Y
 * @since XX.YY.2021
 */
class GaugePanelFieldConfig(
    private val unit: String = "none",
    private val min: Number? = null,
    private val max: Number? = null,
    private val decimals: Number? = null,
    private val noValue: String? = null,
    private val thresholds: Thresholds = Thresholds(),
    private val mappings: List<Mapping> = emptyList(),
    private val nullValueMode: NullValue = NullValue.NULL
) : Json<JSONObject> {
    override fun toJson(): JSONObject = jsonObject {
        "defaults" to jsonObject {
            "unit" to unit
            "min" to min
            "max" to max
            "decimals" to decimals
            "noValue" to noValue
            "thresholds" to thresholds
            "mappings" to JsonArray(mappings)
            "nullValueMode" to nullValueMode.value
        }
    }
}

/**
 * Builder for GaugePanelFieldConfiguration
 * @author X Y
 * @since XX.YY.2021
 */
class GaugePanelFieldConfigBuilder(private val nullValueMode: NullValue = NullValue.NULL) {
    var unit: String = "none"
    var min: Number? = null
    var max: Number? = null
    var decimals: Number? = null
    var noValue: String? = null
    var thresholds: Thresholds = Thresholds()
    var mappings: List<Mapping> = emptyList()

    internal fun createGaugePanelFieldConfig(): GaugePanelFieldConfig = GaugePanelFieldConfig(
        unit,
        min,
        max,
        decimals,
        noValue,
        thresholds,
        mappings,
        nullValueMode
    )

    fun thresholds(mode: ThresholdMode = ThresholdMode.ABSOLUTE, build: ThresholdsBuilder.() -> Unit) {
        val builder = ThresholdsBuilder(mode)
        builder.build()
        thresholds = builder.createThresholds()
    }

    fun mappings(build: MappingsBuilder.() -> Unit) {
        val builder = MappingsBuilder()
        builder.build()
        mappings = builder.mappings
    }
}
