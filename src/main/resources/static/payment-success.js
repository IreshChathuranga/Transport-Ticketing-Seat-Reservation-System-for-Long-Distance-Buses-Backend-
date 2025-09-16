document.addEventListener("DOMContentLoaded", function () {
    // Read from the fragment (after #)
    const params = new URLSearchParams(window.location.search);
    const bookingRef = params.get("order_id");

    if (!bookingRef) {
        alert("Booking reference not found.");
        return;
    }

    const token = localStorage.getItem("jwtToken") || localStorage.getItem("token");
    if (!token) {
        alert("Please login again to see booking details.");
        return;
    }

    fetch(`http://localhost:8080/api/v1/booking/${bookingRef}`, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    })
        .then(res => {
            if (!res.ok) throw new Error("Failed to fetch booking");
            return res.json();
        })
        .then(data => {
            console.log("Booking Data:", data);
            document.getElementById("ref").innerText = bookingRef;
            document.getElementById("status").innerText = data.data?.status || 'N/A';
            // Add more DOM updates if needed
        })
        .catch(err => {
            console.error(err);
            alert("Error loading booking.");
        });
    setTimeout(() => {
        if (confirm('Would you like to go to your booking history?')) {
            window.location.href = 'http://localhost:63342/Long%20Distance%20Bus%20Fronted/html/user-profile.html?_ijt=ueqneacjm2ut2t6l70caipnne5&_ij_reload=RELOAD_ON_SAVE';
        }
    }, 30000);
});
