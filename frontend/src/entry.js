import EventBusPage from './EventBusPage.vue';

if (!window.vertxConsoleRoutes) window.vertxConsoleRoutes = [];
window.vertxConsoleRoutes.push({
    path: '/eventbus',
    component: EventBusPage
});
