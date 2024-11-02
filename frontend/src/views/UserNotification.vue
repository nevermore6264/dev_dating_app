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

        // Hiển thị ElNotification khi nhận thông báo mới
        ElNotification({
          title: 'Notification',
          message: "Your package has been updated to:" + message,
          type: 'success',
          duration: 5000
        });
      });
    });
  }
};
</script>
