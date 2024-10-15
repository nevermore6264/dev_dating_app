<template>
  <el-breadcrumb separator="/">
    <el-breadcrumb-item v-for="(breadcrumb, index) in breadcrumbs" :key="index">
      <router-link :to="breadcrumb.path">{{ breadcrumb.label }}</router-link>
    </el-breadcrumb-item>
  </el-breadcrumb>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router';
import { computed } from 'vue';

// eslint-disable-next-line no-unused-vars
const route = useRoute();
const router = useRouter();

// Compute breadcrumbs based on current route and its meta
const breadcrumbs = computed(() => {
  const matched = router.currentRoute.value.matched;
  return matched.map(route => ({
    path: route?.path,
    label: route?.meta?.breadcrumb,
  }));
});
</script>

<style scoped>
.el-breadcrumb {
  margin-bottom: 20px;
}
</style>
