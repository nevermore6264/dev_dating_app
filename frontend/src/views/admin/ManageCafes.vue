<template>
  <div>
    <h1>Quản Lý Quán Cafe</h1>
    <button @click="fetchCafes">Tải danh sách quán cafe</button>
    <ul>
      <li v-for="cafe in cafes" :key="cafe.id">{{ cafe.name }}</li>
    </ul>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { getAllCafes } from '@/services/cafe-service'; // Nhập service

const cafes = ref([]);

// Hàm để lấy danh sách quán cafe
const fetchCafes = async () => {
  try {
    cafes.value = await getAllCafes(); // Gọi hàm lấy dữ liệu
  } catch (error) {
    console.error(error); // Xử lý lỗi nếu có
  }
};
</script>

<style>
/* Thêm CSS tùy chỉnh nếu cần */
</style>
