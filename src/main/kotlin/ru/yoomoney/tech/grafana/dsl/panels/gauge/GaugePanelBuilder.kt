package ru.yoomoney.tech.grafana.dsl.panels.gauge

import org.json.JSONObject
import ru.yoomoney.tech.grafana.dsl.datasource.Datasource
import ru.yoomoney.tech.grafana.dsl.datasource.Zabbix
import ru.yoomoney.tech.grafana.dsl.generators.PanelLayoutGenerator
import ru.yoomoney.tech.grafana.dsl.metrics.DashboardMetric
import ru.yoomoney.tech.grafana.dsl.metrics.Metrics
import ru.yoomoney.tech.grafana.dsl.metrics.MetricsBuilder
import ru.yoomoney.tech.grafana.dsl.panels.*
import ru.yoomoney.tech.grafana.dsl.panels.repeat.Repeat
import ru.yoomoney.tech.grafana.dsl.panels.repeat.RepeatBuilder
import ru.yoomoney.tech.grafana.dsl.variables.Variable

/**
 * Builder for Stat tab
 * @author X Y
 * @since XX.YY.2021
 */
class GaugePanelBuilder(
    private val title: String,
    private val panelLayoutGenerator: PanelLayoutGenerator
) : PanelBuilder {
    override var bounds: Pair<Int, Int> = PanelBuilder.DEFAULT_BOUNDS

    private var propertiesSetter: (JSONObject) -> Unit = {}

    private var timerange = Timerange()

    private var repeat: Repeat? = null

    var metrics: List<DashboardMetric> = mutableListOf()

    var datasource: Datasource = Zabbix

    var options: GaugePanelDisplayOptions = GaugePanelDisplayOptions()

    var fieldConfig: GaugePanelFieldConfig = GaugePanelFieldConfig()

    override fun properties(propertiesSetter: (JSONObject) -> Unit) {
        this.propertiesSetter = propertiesSetter
    }

    fun options(build: GaugePanelDisplayOptionsBuilder.() -> Unit) {
        val builder = GaugePanelDisplayOptionsBuilder()
        builder.build()
        options = builder.createGaugePanelDisplayOptions()
    }

    fun fieldConfig(build: GaugePanelFieldConfigBuilder.() -> Unit) {
        val builder = GaugePanelFieldConfigBuilder()
        builder.build()
        fieldConfig = builder.createGaugePanelFieldConfig()
    }

    fun repeat(variable: Variable, build: RepeatBuilder.() -> Unit) {
         val builder = RepeatBuilder(variable)
         builder.build()
         repeat = builder.createRepeat()
     }

    inline fun <reified T : Datasource> metrics(build: MetricsBuilder<T>.() -> Unit) {
        datasource = T::class.objectInstance ?: Zabbix
        val builder = MetricsBuilder<T>()
        builder.build()
        metrics = builder.metrics
    }

    fun timerange(build: TimerangeBuilder.() -> Unit) {
        val builder = TimerangeBuilder()
        builder.build()
        timerange = builder.createTimerange()
    }

    internal fun createPanel(): Panel {
        return AdditionalPropertiesPanel(
            GaugePanel(
                MetricPanel(
                    BasePanel(
                        id = panelLayoutGenerator.nextId(),
                        title = title,
                        position = panelLayoutGenerator.nextPosition(bounds.first, bounds.second)
                    ),
                    datasource = datasource,
                    metrics = Metrics(metrics)
                ),
                repeat = repeat,
                timerange = timerange,
                options = options,
                fieldConfig = fieldConfig
            ),
            propertiesSetter
        )
    }
}

fun PanelContainerBuilder.gaugePanel(title: String, build: GaugePanelBuilder.() -> Unit) {
    val builder = GaugePanelBuilder(title, panelLayoutGenerator)
    builder.build()
    panels += builder.createPanel()
}
