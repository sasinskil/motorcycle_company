 const getFormattedDate = () => {
    const today = new Date();
    const year = today.getFullYear();
    const month =
      today.getMonth() + 1 < 10
        ? `0${today.getMonth() + 1}`
        : `${today.getMonth() + 1}`;
    const day =
      today.getDate() < 10 ? `0${today.getDate()}` : `${today.getDate()}`;

    let myDateString = `${year}-${month}-${day}`;

    return myDateString;
  };

const exportTableToExcel = (filename = "") => {
    let downloadLink;
    const dataType = "application/vnd.ms-excel";
    const tableSelect = document.querySelector(".table");
    const tableHTML = tableSelect.outerHTML.replace(/ /g, "%20");

    const currentDate = getFormattedDate();

    // Specify file name
    filename = filename
      ? `${filename}_${currentDate}.xls`
      : `some_data_${currentDate}.xls`;

    // Create download link element
    downloadLink = document.createElement("a");

    document.body.appendChild(downloadLink);

    if (navigator.msSaveOrOpenBlob) {
      var blob = new Blob(["\ufeff", tableHTML], {
        type: dataType
      });
      navigator.msSaveOrOpenBlob(blob, filename);
    } else {
      // Create a link to the file
      downloadLink.href = "data:" + dataType + ", " + tableHTML;

      // Setting the file name
      downloadLink.download = filename;

      //triggering the function
      downloadLink.click();
    }
  };


export {
    exportTableToExcel
};