document
  .getElementById("registerForm")
  .addEventListener("submit", function (e) {
    e.preventDefault();

    const fname = document.getElementById("firstName").value.trim();
    const lname = document.getElementById("lastName").value.trim();
    const dob = document.getElementById("dob").value;
    const email = document.getElementById("email").value.trim();
    const address = document.getElementById("address").value.trim();
    const contact = document.getElementById("contact").value.trim();

    const errorDiv = document.getElementById("errorMessage");
    const successDiv = document.getElementById("successMessage");

    errorDiv.textContent = "";
    successDiv.textContent = "";

    // 1. All fields required
    if (!fname || !lname || !dob || !email || !address || !contact) {
      errorDiv.textContent = "All fields are mandatory.";
      return;
    }

    // 2. DOB validation
    const minDate = new Date("1924-01-01");
    const selectedDate = new Date(dob);
    if (selectedDate <= minDate) {
      errorDiv.textContent = "Choose a date greater than 1/1/1924.";
      return;
    }

    // 3. Contact validation
    if (!/^\d{10}$/.test(contact)) {
      errorDiv.textContent = "Enter a valid contact number.";
      return;
    }

    // 4. Email format
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      errorDiv.textContent = "Enter a valid mail id.";
      return;
    }

    // 5. Generate Passenger ID and password
    const passengerId = Math.floor(10000 + Math.random() * 90000); // 5-digit random number
    const password = fname.slice(0, 4).toLowerCase() + "@123";

    successDiv.innerHTML = `
      Passenger Registration is successful.<br><br>
      <strong>Passenger ID:</strong> ${passengerId}<br>
      <strong>Password:</strong> ${password}
    `;
  });

function confirmReset() {
  const confirmClear = confirm("Is it okay to reset the fields?");
  if (confirmClear) {
    document.getElementById("registerForm").reset();
    document.getElementById("errorMessage").textContent = "";
    document.getElementById("successMessage").textContent = "";
  }
}
