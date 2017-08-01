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
    box-shadow: 1px 0 0px #d1d1d1, 0px 0px 5px rgba(0, 0, 0, 0.25);
}

.header {
    padding: 17.5px 15px 15px;
    border-bottom: 1px solid #d1d1d1;
    color: #4d5258;
    background-color: #f1f1f1;
    background-image: linear-gradient(to bottom, #fafafa 0, #f5f5f5 100%);

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
    background: #fff;
    flex: 1;
    overflow-y: auto;
    padding: 0 15px;
}

.monitored-handler {
    border-bottom: 1px solid #ededed;

    &:last-child {
        border-bottom: none;
    }
}

.charts {
    overflow: auto;
    white-space: pre;
}
</style>

<template>
    <div class="container-fluid eventbus">
        <div class="row" v-if="loaded">
            <div class="col-md-4 handlers">
                <div class="header">
                    <h2 class="title">Handlers</h2>
                    <div class="subtitle">{{ abbreviate(getMetric('handlers').count, 1) }} Registered</div>
                </div>
                <div class="handler-list">
                    <monitored-handler class="monitored-handler" v-for="handler in monitoredHandlers" :key='handler.name' :handler="handler" />
                </div>
            </div>
            <div class="col-md-8 charts">{{ JSON.stringify(busMetrics, null, 4) }}</div>
        </div>
    </div>
</template>

<script>
import abbreviate from 'number-abbreviate';
import MonitoredHandler from './MonitoredHandler.vue';

export default {
    name: 'Event Bus',
    components: {
        'monitored-handler': MonitoredHandler
    },
    data() {
        return {
            busMetrics: {},
            loaded: false
        }
    },
    computed: {
        monitoredHandlers() {
            const prefix = 'vertx.eventbus.handlers.';
            const handlers = [];
            for (let i = 0; i < 40; ++i) {
                handlers.push({ name: 'All handlers', data: this.getMetric('messages.received') });
            }
            for (let [k, v] of Object.entries(this.busMetrics)) {
                if (k.startsWith(prefix)) {
                    handlers.push({ name: k.substring(prefix.length), data: v });
                }
            }
            return handlers;
        }
    },
    beforeMount() {
        ''
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