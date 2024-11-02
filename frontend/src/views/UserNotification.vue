<template>
  <div>
  </div>
</template>

<script>
import {onMounted} from 'vue';
import {connectSSE} from '@/services/sse-client';
import {ElNotification} from 'element-plus';

export default {
  name: 'UserNotification',
  setup() {
    onMounted(() => {
      console.log("UserNotification component mounted");

      connectSSE((message) => {
        console.log("Received message:", message);
        localStorage.setItem("plan", message);

        // Lấy vai trò (role) từ localStorage
        const role = localStorage.getItem("role");

        // Kiểm tra nếu role không phải là "admin" thì hiển thị thông báo
        if (role !== "admin") {
          ElNotification({
            title: 'Notification',
            message: "Your package has been updated to: " + message,
            type: 'success',
            duration: 5000
          });
        }
      });
    });
  }
};
</script>
