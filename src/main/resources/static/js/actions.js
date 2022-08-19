
function generateReport(){
    fetch('http://localhost:8080/api/v1/airtel-payments/payments-report')
    .then(response => response.json())
    .then(data => alert(data.operation_description));
}

function downloadReport(){
  fetch("http://localhost:8080/api/v1/airtel-payments/download/")
  .then((res) => { return res.blob(); })
  .then((data) => {
   var a = document.createElement("a");
   a.href = window.URL.createObjectURL(data);
   a.download = "Payments";
   a.click();
})
}