document.getElementById("passengerName").innerText =
  (JSON.parse(localStorage.getItem("registeredUserData")) || {}).firstName ||
  "Passenger";

function goHome() {
  location.reload();
}

function showProfile() {
  const modal = document.getElementById("profileModal");
  const detailsDiv = document.getElementById("profileDetails");
  const storedData =
    JSON.parse(localStorage.getItem("registeredUserData")) || {};

  detailsDiv.innerHTML = `
    <p><strong>First Name:</strong> ${storedData.firstName || ""}</p>
    <p><strong>Last Name:</strong> ${storedData.lastName || ""}</p>
    <p><strong>DOB:</strong> ${storedData.dob || ""}</p>
    <p><strong>Email:</strong> ${storedData.email || ""}</p>
    <p><strong>Address:</strong> ${storedData.address || ""}</p>
    <p><strong>Contact:</strong> ${storedData.contact || ""}</p>
  `;

  modal.style.display = "flex";

  document.getElementById("editBtn").onclick = () => {
    document.getElementById("editFields").style.display = "block";
    document.getElementById("editFirstName").value = storedData.firstName || "";
    document.getElementById("editLastName").value = storedData.lastName || "";
    document.getElementById("editDob").value = storedData.dob || "";
    document.getElementById("editEmail").value = storedData.email || "";
    document.getElementById("editAddress").value = storedData.address || "";
    document.getElementById("editContact").value = storedData.contact || "";
  };

  document.getElementById("saveBtn").onclick = () => {
    const updatedData = {
      firstName: document.getElementById("editFirstName").value.trim(),
      lastName: document.getElementById("editLastName").value.trim(),
      dob: document.getElementById("editDob").value,
      email: document.getElementById("editEmail").value.trim(),
      address: document.getElementById("editAddress").value.trim(),
      contact: document.getElementById("editContact").value.trim(),
      userId: storedData.userId,
      password: storedData.password,
    };
    localStorage.setItem("registeredUserData", JSON.stringify(updatedData));
    alert("Profile updated successfully!");
    showProfile();
  };
}

function closeProfile() {
  document.getElementById("profileModal").style.display = "none";
  document.getElementById("editFields").style.display = "none";
}

function showUpcoming() {
  document.getElementById("tripDetails").innerHTML = `
    <h3>Upcoming Trips</h3>
    <p>Flight: IndiGo | Delhi to Mumbai | 25-Aug-2025 | ₹4500</p>
    <p>Flight: Air India | Chennai to Kolkata | 12-Sep-2025 | ₹5200</p>
    <p>Flight: GoAir | Hyderabad to Bangalore | 05-Oct-2025 | ₹3900</p>

  `;
}

function showCancelled() {
  document.getElementById("tripDetails").innerHTML = `
    <h3>Cancelled Trips</h3>
    <p>Flight: SpiceJet | Mumbai to Goa | 10-Jul-2025 | ₹3200</p>
    <p>Flight: AirAsia | Pune to Jaipur | 18-Jul-2025 | ₹3500</p>
    <p>Flight: Vistara | Kochi to Chennai | 22-Jul-2025 | ₹4100</p>

  `;
}

function showCompleted() {
  document.getElementById("tripDetails").innerHTML = `
    <h3>Completed Trips</h3>
    <p>Flight: Vistara | Kolkata to Delhi | 01-Jul-2025 | ₹4000</p>
    <p>Flight: SpiceJet | Goa to Mumbai | 15-Jul-2025 | ₹3200</p>
    <p>Flight: Air India | Hyderabad to Bangalore | 20-Jul-2025 | ₹3700</p>

  `;
}

function logout() {
  window.location.href = "loginPage.html";
}

function searchFlights() {
  alert("Search clicked. (To be implemented)");
}
