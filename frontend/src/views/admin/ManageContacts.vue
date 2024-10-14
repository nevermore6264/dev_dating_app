<template>
  <div>
    <h1>Manage Contact Forms</h1>

    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>Full Name</th>
        <th>Email</th>
        <th>Phone</th>
        <th>Message</th>
        <th>Submission Date</th>
        <th>Response Status</th>
        <th style="width: 120px">Operations</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="form in contactForms" :key="form.id">
        <td>{{ form.id }}</td>
        <td>{{ form.fullName }}</td>
        <td>{{ form.email }}</td>
        <td>{{ form.phoneNumber }}</td>
        <td>{{ form.message }}</td>
        <td>{{ formatSubmissionDate(form?.submissionDate) }}</td>
        <td>{{ form.responseStatus }}</td>
        <td>
          <el-button type="primary" @click="replyToForm(form.id)" :disabled="form.responseStatus === 'Replied'">
            {{ form.responseStatus === 'Replied' ? 'Replied' : 'Reply' }}
          </el-button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { ElMessage, ElNotification } from 'element-plus';
import {
  getAllContactForms,
  replyToContactForm as replyToContactFormAPI
} from '@/services/admin/admin-contact-service';
import { format } from 'date-fns'; // Import date-fns format function

const contactForms = ref([]);

// Load contact forms when component is mounted
onMounted(async () => {
  await fetchContactForms();
});

// Fetch contact forms
const fetchContactForms = async () => {
  try {
    contactForms.value = await getAllContactForms();
  } catch (error) {
    ElMessage.error('Failed to fetch contact forms. Please try again later.');
  }
};

// Reply to a contact form
const replyToForm = async (id) => {
  if (confirm('Are you sure you want to reply to this contact form?')) {
    try {
      await replyToContactFormAPI(id);
      await fetchContactForms();
      ElNotification({
        title: 'Success',
        message: 'Replied to contact form successfully.',
        type: 'success',
      });
    } catch (error) {
      ElNotification({
        title: 'Error',
        message: error?.message,
        type: 'error',
      });
    }
  }
};

const formatSubmissionDate = (dateString) => {
  if (!dateString) return 'Not available'; // Handle null or undefined dates
  return format(new Date(dateString), 'MMMM dd, yyyy HH:mm'); // Format: October 15, 2024 14:30
};
</script>
