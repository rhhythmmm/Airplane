document.getElementById("passengerName").innerText =
  localStorage.getItem("passengerName") || "Passenger";

function goHome() {
  location.reload();
}

function showProfile() {
  alert("Profile section clicked.");
}

function showUpcoming() {
  document.getElementById("tripDetails").innerHTML = `
    <h3>Upcoming Trips</h3>
    <p>Flight: IndiGo | Delhi to Mumbai | 25-Aug-2025 | ₹4500</p>
  `;
}

function showCancelled() {
  document.getElementById("tripDetails").innerHTML = `
    <h3>Cancelled Trips</h3>
    <p>Flight: SpiceJet | Mumbai to Goa | 10-Jul-2025 | ₹3200</p>
  `;
}

function showCompleted() {
  document.getElementById("tripDetails").innerHTML = `
    <h3>Completed Trips</h3>
    <p>Flight: Vistara | Kolkata to Delhi | 01-Jul-2025 | ₹4000</p>
  `;
}

function logout() {
  window.location.href = "loginPage.html";
}

function searchFlights() {
  alert("Search clicked. (To be implemented)");
}
