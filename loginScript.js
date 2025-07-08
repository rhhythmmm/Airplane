document.getElementById("loginForm").addEventListener("submit", function (e) {
  e.preventDefault();

  const userId = document.getElementById("userId").value.trim();
  const password = document.getElementById("password").value.trim();
  const errorDiv = document.getElementById("errorMessage");

  // Static credentials
  const validUsers = [
    { id: "12345", pwd: "password123" },
    { id: "54321", pwd: "welcome2024" },
    { id: "11111", pwd: "airlineadmin" },
  ];

  errorDiv.textContent = "";

  if (!userId || !password) {
    errorDiv.textContent = "Both fields are mandatory.";
    return;
  }

  if (!/^\d{1,5}$/.test(userId)) {
    errorDiv.textContent = "ID not valid";
    return;
  }

  if (password.length < 6 || password.length > 30) {
    errorDiv.textContent = "Password not valid";
    return;
  }

  const userMatch = validUsers.find(
    (u) => u.id === userId && u.pwd === password
  );

  if (userMatch) {
    window.location.href = "homePage.html"; // Replace with actual home page
  } else if (!validUsers.some((u) => u.id === userId)) {
    errorDiv.textContent = "ID not valid";
  } else if (!validUsers.some((u) => u.pwd === password)) {
    errorDiv.textContent = "Password not valid";
  } else {
    errorDiv.textContent = "Both ID/password not valid";
  }
});
