<style lang="scss" scoped>
.multiline {
    padding: 15px 20px 20px 5px;
}

.rate-title {
    font-size: 16px;
    padding: 0px 10px 10px;
}
</style>

<template>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-6">
                <div class="card-pf multiline">
                    <div class="rate-title">Messages received/sec</div>
                    <pf-multi-line :height="250" :data="receivedRate" chartType="area" :maxDisplayed="10"></pf-multi-line>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card-pf multiline">
                    <div class="rate-title">Messages published/sec</div>
                    <pf-multi-line :height="250" :data="publishRate" chartType="area" :maxDisplayed="10"></pf-multi-line>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card-pf multiline">
                    <div class="rate-title">Messages delivered/sec</div>
                    <pf-multi-line :height="250" :data="deliveredRate" chartType="area" :maxDisplayed="10"></pf-multi-line>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card-pf multiline">
                    <div class="rate-title">Reply failures/sec</div>
                    <pf-multi-line :height="250" :data="replyFailuresRate" chartType="area" :maxDisplayed="10" :extraChartOptions="{color: { pattern: ['#a30000', '#ec7a08', '#f0ab00'] } }"></pf-multi-line>
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
        receivedRate() {
            return this.getThroughputMeterC3Data('messages.received');
        },
        publishRate() {
            return this.getThroughputMeterC3Data('messages.published');
        },
        deliveredRate() {
            return this.getThroughputMeterC3Data('messages.delivered');
        },
        replyFailuresRate() {
            return this.getThroughputMeterC3Data('messages.reply-failures');
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