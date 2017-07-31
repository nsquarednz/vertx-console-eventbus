<style lang="scss" scoped>
.eventbus,
.eventbus .row,
.handlers,
.charts {
    height: 100%;
}

.handlers {
    padding: 0;
    display: flex;
    flex-direction: column;
    background: #fff;
    box-shadow: 1px 0 0px #d1d1d1, 0px 0px 5px rgba(0, 0, 0, 0.25);
}


.charts {}

.header {
    padding: 17.5px 15px 15px;
    border-bottom: 1px solid #d1d1d1;

    .title {
        display: inline;
    }

    .subtitle {
        font-weight: bold;
        text-transform: uppercase;
        float: right;
        line-height: 24.2px;
    }
}

.handler-list {
    flex: 1;
}
</style>

<template>
    <div class="container-fluid eventbus">
        <div class="row" v-if="loaded">
            <div class="col-md-4 handlers">
                <div class="header">
                    <h2 class="title">Handlers</h2>
                    <div class="subtitle">{{ abbreviate(getMetric('handlers').count) }} Registered</div>
                </div>
                <div class="handler-list">
    
                </div>
            </div>
            <div class="col-md-8 charts">
                {{ busMetrics }}
    
            </div>
        </div>
    </div>
</template>

<script>
import abbreviate from 'number-abbreviate';

export default {
    name: 'Event Bus',
    data() {
        return {
            busMetrics: {},
            loaded: false
        }
    },
    beforeMount() {
        const updateData = () => {
            this.$http.get(window.location.pathname + '/busmetrics')
                .then(response => response.data)
                .then(data => {
                    this.busMetrics = data
                    this.loaded = true;
                });
        };
        updateData();
        this.updateTask = setInterval(updateData, 1000);

        // Third party
        this.abbreviate = abbreviate;
    },
    beforeDestroy() {
        clearInterval(this.updateTask);
    },
    methods: {
        getMetric(name) {
            return this.busMetrics['vertx.eventbus.' + name];
        }
    }
}
</script>