export function connectSSE() {
  const eventSource = new EventSource("http://localhost:8088/api/subscribe");

  eventSource.onmessage = function(event) {
    console.log("Notification:", event.data);
  };

  eventSource.onerror = function() {
    console.log("Connection closed");
  };
}