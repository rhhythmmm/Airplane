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
    const loginLinkDiv = document.getElementById("loginLink");

    errorDiv.textContent = "";
    successDiv.textContent = "";
    loginLinkDiv.innerHTML = "";

    if (!fname || !lname || !dob || !email || !address || !contact) {
      errorDiv.textContent = "All fields are mandatory.";
      return;
    }

    const minDate = new Date("1924-01-01");
    const selectedDate = new Date(dob);
    if (selectedDate <= minDate) {
      errorDiv.textContent = "Choose a date greater than 1/1/1924.";
      return;
    }

    if (!/^\d{10}$/.test(contact)) {
      errorDiv.textContent = "Enter a valid contact number.";
      return;
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      errorDiv.textContent = "Enter a valid mail id.";
      return;
    }

    const passengerId = Math.floor(10000 + Math.random() * 90000).toString();
    const password = fname.slice(0, 4).toLowerCase() + "@123";

    const userData = {
      passengerId: passengerId,
      password: password,
      firstName: fname,
      lastName: lname,
      dob: dob,
      email: email,
      address: address,
      contact: contact,
    };

    localStorage.setItem("registeredUserData", JSON.stringify(userData));
    localStorage.setItem("passengerName", fname);

    successDiv.innerHTML = `
    <p>Passenger Registration is successful.</p>
    <p><strong>Passenger ID:</strong> ${passengerId}</p>
    <p><strong>Password:</strong> ${password}</p>
  `;

    loginLinkDiv.innerHTML = `
    <a href="loginPage.html" class="loginRedirectLink">Click here to Login</a>
  `;
  });

function confirmReset() {
  const confirmClear = confirm("Is it okay to reset the fields?");
  if (confirmClear) {
    document.getElementById("registerForm").reset();
    document.getElementById("errorMessage").textContent = "";
    document.getElementById("successMessage").textContent = "";
    document.getElementById("loginLink").innerHTML = "";
  }
}
