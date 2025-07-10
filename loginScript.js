const validUsers = [
  {
    id: "12345",
    pwd: "password123",
    profile: {
      firstName: "John",
      lastName: "Doe",
      dob: "1990-05-10",
      email: "john@example.com",
      address: "123 Street",
      contact: "9876543210",
    },
  },
  {
    id: "54321",
    pwd: "welcome2024",
    profile: {
      firstName: "Alice",
      lastName: "Smith",
      dob: "1992-03-15",
      email: "alice@example.com",
      address: "456 Avenue",
      contact: "9123456789",
    },
  },
  {
    id: "11111",
    pwd: "airlineadmin",
    profile: {
      firstName: "Admin",
      lastName: "User",
      dob: "1985-01-01",
      email: "admin@airline.com",
      address: "Admin HQ",
      contact: "9000000000",
    },
  },
];

document.getElementById("loginForm").addEventListener("submit", function (e) {
  e.preventDefault();

  const userId = document.getElementById("userId").value.trim();
  const password = document.getElementById("password").value.trim();
  const errorDiv = document.getElementById("errorMessage");

  errorDiv.textContent = "";

  const storedUserData = JSON.parse(localStorage.getItem("registeredUserData"));

  const isStoredMatch =
    storedUserData &&
    storedUserData.passengerId === userId &&
    storedUserData.password === password;

  const matchedHardcoded = validUsers.find(
    (u) => u.id === userId && u.pwd === password
  );

  if (isStoredMatch) {
    localStorage.setItem("passengerName", storedUserData.firstName);
    window.location.href = "homePage.html";
  } else if (matchedHardcoded) {
    const profile = matchedHardcoded.profile;
    const saveObj = {
      passengerId: matchedHardcoded.id,
      password: matchedHardcoded.pwd,
      firstName: profile.firstName,
      lastName: profile.lastName,
      dob: profile.dob,
      email: profile.email,
      address: profile.address,
      contact: profile.contact,
    };
    localStorage.setItem("registeredUserData", JSON.stringify(saveObj));
    localStorage.setItem("passengerName", profile.firstName);
    window.location.href = "homePage.html";
  } else {
    errorDiv.textContent = "Invalid ID or Password.";
  }
});
