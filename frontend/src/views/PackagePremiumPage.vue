<template>
  <div class="layout-container">
    <!-- Sidebar -->
    <LoveBellSidebar />
    <!-- Sidebar component without custom CSS -->

    <!-- Main content -->
    <div class="subscription-container">
      <h2 class="subscription-header">LOVE BELL PACKAGES</h2>
      <hr />
      <!-- Grid for the Subscription Packages -->
      <div class="subscription-grid">
        <!-- Headers Row -->
        <div></div>
        <div class="package-header plus-header">LoveBell Free</div>
        <div class="package-header gold-header">LoveBell Trial</div>
        <div class="package-header platinum-header">LoveBell Premium</div>

        <!-- Feature Rows -->
        <template v-for="(feature, index) in features" :key="index">
          <div class="feature-title">{{ feature.title }}</div>
          <div
            class="package-feature"
            v-for="(plan, planIndex) in feature.plans"
            :key="planIndex"
          >
            <span class="material-icons" :class="{ locked: !plan.available }">
              {{ plan.available ? "check_circle" : "lock" }}
            </span>
          </div>
        </template>

        <!-- Pricing Row -->
        <div></div>
        <template v-for="(plan, index) in subscriptionPlans" :key="index">
          <button :class="['price-button', plan.buttonClass]">
            Có giá từ {{ plan.price }} đ
          </button>
        </template>
      </div>
    </div>
  </div>
</template>

<script>
import LoveBellSidebar from "@/views/sidebar/LoveBellSidebar.vue";
import { getAllSubscriptionPlans } from "@/services/package-service.js";
export default {
  data() {
    return {
      subscriptionPlans: [
        // { planId: 1, name: 'Free', price: 0, buttonClass: 'pink' },
        // { planId: 2, name: 'Trial', price: 50400, buttonClass: 'gold' },
        // { planId: 3, name: 'Premium', price: 93600, buttonClass: 'black' }
      ],
      features: [
        {
          title: "Thích không giới hạn",
          plans: [
            { available: true },
            { available: true },
            { available: true },
          ],
        },
        {
          title: "Xem ai Thích bạn",
          plans: [
            { available: false },
            { available: true },
            { available: true },
          ],
        },
        {
          title: "Lượt Thích ưu tiên",
          plans: [
            { available: false },
            { available: false },
            { available: true },
          ],
        },
        {
          title: "Quay lại không giới hạn",
          plans: [
            { available: true },
            { available: true },
            { available: true },
          ],
        }
     
      ],
    };
  },
  components: {
    LoveBellSidebar,
  },
  mounted() {
    this.fetchSubscriptionPlans();
  },
  methods: {
    async fetchSubscriptionPlans() {
      try {
        const plans = await getAllSubscriptionPlans();
        this.subscriptionPlans = plans.map((plan, index) => ({
          ...plan,
          buttonClass: ["pink", "gold", "black"][index],
        }));
      } catch (error) {
        console.error("Error fetching subscription plans:", error);
      }
    },
  },
};
</script>

<style scoped>
/* Layout container to place sidebar and content side by side */
.layout-container {
  display: flex;
  flex-direction: row; /* Sidebar and main content aligned horizontally */
  align-items: flex-start; /* Align both items at the top */
  background: #f8f9fa; /* Light background for the layout */
  min-height: 100vh; /* Make sure the layout takes full viewport height */
}

/* Sidebar styling */
.sidebar {
  width: 250px; /* Fixed width for sidebar */
  padding: 20px;
  background: #343a40; /* Dark background for sidebar */
  color: white; /* Text color */
  box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1); /* Subtle shadow to separate sidebar */
}

/* Main content styles */
.subscription-container {
  flex-grow: 1; /* Allow main content to take remaining space */
  padding: 40px; /* More padding for a spacious layout */
  background: #ffffff; /* White background for main content */
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.05); /* Light shadow to lift the container */
  border-radius: 15px; /* Rounded corners for a softer look */
  margin: 20px;
}

/* Header styling */
.subscription-header {
  font-size: 32px; /* Larger font size */
  font-weight: bold;
  margin-bottom: 20px;
  text-align: center;
  padding: 10px;
  color: #ff6699;
  border-radius: 15px;
  animation: fadeInDown 1s ease-in-out; /* Animation on load */
}

/* Subscription grid */
.subscription-grid {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr;
  gap: 15px; /* Increased gap for better spacing */
  justify-items: center;
  align-items: center;
  animation: fadeIn 1.2s ease-in-out; /* General fade-in animation */
}

/* Package header */
.package-header {
  font-weight: bold;
  padding: 20px;
  border-radius: 20px;
  color: black;
  font-size: 18px;
  text-align: center;
  width: 80%; /* Wider for better visual */
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.2); /* Enhanced shadow */
  transition: transform 0.3s ease, box-shadow 0.3s ease; /* Smooth transition */
}

/* Smooth hover effect for header */
.package-header:hover {
  transform: translateY(-5px) scale(1.05); /* Lift and enlarge on hover */
  box-shadow: 0px 8px 20px rgba(0, 0, 0, 0.3); /* Deeper shadow on hover */
}

/* Gradient styles */
.plus-header {
  background: linear-gradient(135deg, #ffccf9 0%, #ffb6c1 100%);
}

.gold-header {
  background: linear-gradient(135deg, #ffd700 0%, #fffacd 100%);
}

.platinum-header {
  background: linear-gradient(135deg, #d3d3d3 0%, #f0f0f0 100%);
}

/* Feature titles */
.feature-title {
  font-weight: bold;
  text-align: left;
  font-size: 18px;
  padding: 20px;
  animation: slideInLeft 0.8s ease; /* Slide effect on load */
}

/* Feature icons */
.package-feature {
  font-size: 18px;
}

.locked {
  color: gray;
}

.material-icons {
  font-size: 30px;
  color: #4caf50;
  transition: transform 0.2s ease; /* Small scale on hover */
}

.package-feature:hover .material-icons {
  transform: scale(1.2); /* Enlarge icons when hovering */
}

/* Locked feature */
.locked .material-icons {
  color: gray;
}

/* Pricing button common styles */
.price-button {
  padding: 15px;
  border-radius: 25px;
  font-weight: bold;
  color: white;
  text-align: center;
  width: 60%;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  border: none; /* Remove default button border */
  outline: none; /* Remove focus outline */
}

/* Button hover effect */
.price-button:hover {
  transform: translateY(-5px) scale(1.1); /* Lift and enlarge on hover */
  box-shadow: 0px 8px 20px rgba(0, 0, 0, 0.3); /* Deepened shadow on hover */
}

/* Button press effect */
.price-button:active {
  transform: translateY(0) scale(1); /* Reset scale */
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2); /* Reduced shadow on press */
}

/* Specific styles for each button */
.pink {
  background: linear-gradient(135deg, #ff70a1, #ff4081);
}

.gold {
  background: linear-gradient(135deg, #ffd700, #ffcc00);
}

.black {
  background: linear-gradient(135deg, #2c2c2c, #3a3a3a);
}

/* Keyframe animations */
@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

@keyframes fadeInDown {
  0% {
    opacity: 0;
    transform: translateY(-20px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes slideInLeft {
  0% {
    opacity: 0;
    transform: translateX(-30px);
  }
  100% {
    opacity: 1;
    transform: translateX(0);
  }
}
</style>
