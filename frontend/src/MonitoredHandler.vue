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
    }
}
</script>