<style lang="scss" scoped>
    .example {
        padding: 30px;
        font-size: 18px;
    }
</style>

<template>
    <div class="eventbus">
        {{ busMetrics }}
    </div>
</template>

<script>
export default {
    name: 'Event Bus Details',
    data() {
        return {
            busMetrics: {}
        }
    },
    beforeMount() {
        const updateData = () => {
            this.$http.get(window.location.pathname + '/busmetrics')
                .then(response => response.data)
                .then(data => this.busMetrics = data);
        };
        updateData();
        this.updateTask = setInterval(updateData, 1000);
    },
    beforeDestroy() {
        clearInterval(this.updateTask);
    }
}
</script>