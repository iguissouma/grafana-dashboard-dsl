package ru.yoomoney.tech.grafana.dsl.panels.gauge

import ru.yoomoney.tech.grafana.dsl.json.jsonObject
import ru.yoomoney.tech.grafana.dsl.panels.Panel
import ru.yoomoney.tech.grafana.dsl.panels.Timerange
import ru.yoomoney.tech.grafana.dsl.panels.repeat.Repeat

/**
 * Gauge panel presents text from defined metric
 * https://grafana.com/docs/grafana/latest/panels/visualizations/gauge-panel/
 * @author X Y
 * @since XX.YY.2021
 */
class GaugePanel(
    private val basePanel: Panel,
    private val timerange: Timerange = Timerange(),
    private val repeat: Repeat? = null,
    private val fieldConfig: GaugePanelFieldConfig = GaugePanelFieldConfig(),
    private val options: GaugePanelDisplayOptions = GaugePanelDisplayOptions()
) : Panel {

    override fun toJson() = jsonObject(basePanel.toJson()) {
        "type" to "gauge"
        embed(timerange)
        repeat?.let { embed(it) }
        "fieldConfig" to fieldConfig
        "options" to options
    }
}
