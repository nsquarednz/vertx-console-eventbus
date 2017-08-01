<style lang="scss" scoped>
.handler-item {
    padding: 15px 0;
}

.name {
    font-size: 14px;
}

.received {
    font-weight: bold;
    text-transform: uppercase;
}
</style>

<template>
    <div class="handler-item">
        <div class="left">
            <div class="name">{{ handler.name }}</div>
            <div class="received">{{ abbreviate(handler.data.count, 1) }} msgs received</div>
        </div>
        {{ handler }}
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