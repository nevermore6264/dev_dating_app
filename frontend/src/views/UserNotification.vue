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

        // Hiển thị ElNotification khi nhận thông báo mới
        ElNotification({
          title: 'Notification',
          message: message,
          type: 'success', // Bạn có thể thay đổi thành 'info', 'warning', hoặc 'error' tùy theo loại thông báo
          duration: 5000 // Tự động ẩn sau 5 giây
        });
      });
    });
  }
};
</script>
