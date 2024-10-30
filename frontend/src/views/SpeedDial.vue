<template>
  <div>
    <!-- Nút SpeedDial -->
    <div class="speed-dial" @click="drawerVisible = true">
      <span>+</span>
    </div>

    <!-- Drawer Form for Contact Support -->
    <el-drawer v-model="drawerVisible" :direction="direction" size="30%">
      <!-- Header Slot -->
      <template #header>
        <h4>Contact Support</h4>
      </template>

      <!-- Nội dung form bên trong Drawer -->
      <template #default>
        <form @submit.prevent="submitContactForm">
          <div class="form-field">
            <label for="full_name">Full Name</label>
            <el-input
                id="full_name"
                v-model="contactForm.full_name"
                placeholder="Enter your full name"
                class="styled-input"
            ></el-input>
          </div>

          <div class="form-field">
            <label for="email">Email</label>
            <el-input
                id="email"
                v-model="contactForm.email"
                placeholder="Enter your email"
                class="styled-input"
            ></el-input>
          </div>

          <div class="form-field">
            <label for="phone_number">Phone Number</label>
            <el-input
                id="phone_number"
                v-model="contactForm.phone_number"
                placeholder="Enter your phone number"
                class="styled-input"
            ></el-input>
          </div>

          <div class="form-field">
            <label for="message">Message</label>
            <el-input
                type="textarea"
                id="message"
                v-model="contactForm.message"
                placeholder="Write your message"
                class="styled-input"
            ></el-input>
          </div>
        </form>
      </template>

      <!-- Footer Slot -->
      <template #footer>
        <div style="text-align: right">
          <el-button @click="cancelForm">Cancel</el-button>
          <el-button type="primary" @click="submitContactForm">Send</el-button>
        </div>
      </template>
    </el-drawer>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import {insertContactForm} from "@/services/contact-service";

const drawerVisible = ref(false);
const direction = ref('rtl'); // Tùy chỉnh hướng của Drawer

const contactForm = ref({
  full_name: '',
  email: '',
  phone_number: '',
  message: ''
});

const submitContactForm = async () => {
  if (!contactForm.value.email || !contactForm.value.full_name || !contactForm.value.phone_number) {
    ElMessage.error("Please fill in all required fields.");
    return;
  }

  try {
    const result = await insertContactForm(contactForm);
    console.log('Contact form submitted:', result);
  } catch (error) {
    console.error(error.message);
  }
};

const cancelForm = () => {
  drawerVisible.value = false;
};
</script>

<style scoped>
.speed-dial {
  position: fixed;
  bottom: 100px;
  right: 20px;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background-color: #007bff;
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 24px;
  cursor: pointer;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
  transition: background-color 0.3s ease;
}

.speed-dial span {
  margin-bottom: 5px !important;
}

.speed-dial:hover {
  background-color: #0056b3;
}

.form-field {
  margin-bottom: 20px;
}

.form-field label {
  display: block;
  font-weight: 600;
  margin-bottom: 5px;
}

.styled-input ::v-deep(.el-input__inner),
.styled-input ::v-deep(.el-textarea__inner) {
  border-radius: 5px;
  font-size: 14px;
  transition: border-color 0.3s;
}
</style>
