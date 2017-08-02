<style lang="scss" scoped>
.multiline {
    padding: 10px 20px 20px 5px;
}

.rate-title {
    font-size: 16px;
    padding: 0px 10px 5px;
}
</style>

<template>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-6">
                <div class="card-pf multiline">
                    <div class="rate-title">Messages Received</div>
                    <pf-multi-line :height="288" :data="receivedRate" chartType="area" :maxDisplayed="10"></pf-multi-line>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card-pf multiline">
                    <div class="rate-title">Messages Published</div>
                    <pf-multi-line :height="288" :data="publishRate" chartType="area" :maxDisplayed="10"></pf-multi-line>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card-pf multiline">
                    <div class="rate-title">Messages Delivered</div>
                    <pf-multi-line :height="288" :data="deliveredRate" chartType="area" :maxDisplayed="10"></pf-multi-line>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card-pf multiline">
                    <div class="rate-title">Reply Failures</div>
                    <pf-multi-line :height="288" :data="replyFailuresRate" chartType="area" :maxDisplayed="10"></pf-multi-line>
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