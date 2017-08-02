<style lang="scss" scoped>
.multiline {
    padding: 20px 20px 20px 5px;
}
</style>

<template>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-6">
                <div class="card-pf">
                    <pf-multi-line :height="288" :data="publishRate" chartType="area" :maxDisplayed="10"></pf-multi-line>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card-pf">
                    <pf-multi-line :height="288" :data="deliveredRate" chartType="area" :maxDisplayed="10"></pf-multi-line>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card-pf">
                    <pf-multi-line :height="288" :data="deliveredRate" chartType="area" :maxDisplayed="10"></pf-multi-line>
                </div>
            </div>
    
            <div class="col-md-6">
                <div class="card-pf">    
                    <pf-multi-line :height="288" :data="deliveredRate" chartType="area" :maxDisplayed="10"></pf-multi-line>
                </div>
            </div>
    
        </div>
    </div>
</template>

<script>
export default {
    props: {
        busMetrics: Object,
        getMetric: Function
    },
    computed: {
        publishRate() {
            return this.getThroughputMeterC3Data('messages.published');
        },
        sentRate() {
            return this.getThroughputMeterC3Data('messages.sent');
        },
        deliveredRate() {
            return this.getThroughputMeterC3Data('messages.delivered');
        }
    },
    methods: {
        getThroughputMeterC3Data(name) {
            const metric = this.getMetric(name);

            const oneMin = parseFloat(metric.oneMinuteRate);
            const fiveMin = parseFloat(metric.fiveMinuteRate);
            const fifteenMin = parseFloat(metric.fiveMinuteRate);

            return {
                indices: [new Date()],
                values: {
                    '1 minute': oneMin.toFixed(2),
                    '5 minutes': fiveMin.toFixed(2),
                    '15 minutes': fifteenMin.toFixed(2)
                }
            }
        }
    }
}
</script>