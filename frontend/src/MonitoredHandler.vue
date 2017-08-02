<style lang="scss" scoped>
.handler-item {
    padding: 15px 0;
    display: flex;
}

.name {
    font-size: 14px;
}

.received {
    font-weight: bold;
    text-transform: uppercase;
}

.left {
    display: inline-block;
}

.right {
    margin-left: auto;
    display: flex;
}

.rate {
    display: inline;
    margin-left: 7.5px;
    text-align: center;

    .value {
        font-weight: bold;
    }

    &.s-1 {
        color: #00659c;
    }

    &.m-1 {
        color: #0088ce;
    }

    &.m-5 {
        color: #39a5dc;
    }

    &.m-15 {
        color: #3f9c35;
    }
}
</style>

<template>
    <div class="handler-item">
        <div class="left">
            <div class="name">{{ handler.name }}</div>
            <div class="received">{{ abbreviate(handler.data.count, 2) }} received{{ handler.data.pending === undefined ? '' : `, ${abbreviate(handler.data.pending, 2)} pending` }}</div>
        </div>
        <div class="right">
            <div class="rate s-1">
                <div class="period">Rate</div>
                <div class="value">{{ abbreviate(oneSecondRate, 1) }}/s</div>
            </div>
            <div class="rate m-1">
                <div class="period">1m</div>
                <div class="value">{{ abbreviate(handler.data.oneMinuteRate.toFixed(2), 2) }}</div>
            </div>
            <div class="rate m-5">
                <div class="period">5m</div>
                <div class="value">{{ abbreviate(handler.data.fiveMinuteRate.toFixed(2), 2) }}</div>
            </div>    
            <div class="rate m-15">
                <div class="period">15m</div>
                <div class="value">{{ abbreviate(handler.data.fiveMinuteRate.toFixed(2), 2) }}</div>
            </div>

        </div>
    </div>
</template>

<script>
import abbreviate from 'number-abbreviate';

export default {
    props: {
        handler: Object
    },
    beforeMount() {
        this.abbreviate = abbreviate;
        this.prevCount = -1;
        this.prevTime = -1;
    },
    computed: {
        oneSecondRate() {
            if (this.handler.data.oneSecondRate === undefined) {
                let rate;
                const currCount = this.handler.data.count;
                const currTime = performance.now();
                if (this.prevCount === -1) {
                    rate = 0;
                } else {
                    const deltaT = (currTime - this.prevTime) / 1000;
                    rate = Math.floor((this.handler.data.count - this.prevCount) / deltaT);
                }
                this.prevCount = currCount;
                this.prevTime = currTime;
                return rate;
            } else {
                return this.handler.data.oneSecondRate;
            }
        }
    }
}
</script>