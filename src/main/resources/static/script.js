const API_URL = 'http://localhost:8081/api/s3';

function uploadFile() {
  const fileInput = document.getElementById('fileInput');
  const file = fileInput.files[0];
  const responseBox = document.getElementById('response');
  const errorBox = document.getElementById('error');

  if (!file) {
    errorBox.textContent = "Please choose a file to upload.";
    return;
  }

  const formData = new FormData();
  formData.append("file", file);

  fetch(`${API_URL}/upload`, {
    method: "POST",
    body: formData,
  })
    .then((res) => res.text())
    .then((msg) => {
      responseBox.textContent = msg;
      errorBox.textContent = "";
    })
    .catch((err) => {
      errorBox.textContent = "Upload failed.";
      responseBox.textContent = "";
    });
}

function downloadFile() {
  const key = document.getElementById('downloadKey').value.trim();
  const responseBox = document.getElementById('response');
  const errorBox = document.getElementById('error');

  if (!key) {
    errorBox.textContent = "Please enter the filename to download.";
    return;
  }

  fetch(`${API_URL}/download-direct?keyName=${encodeURIComponent(key)}`)
    .then(response => {
      if (!response.ok) {
        throw new Error("File not found or download error");
      }
      return response.blob();
    })
    .then(blob => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = key;
      document.body.appendChild(a);
      a.click();
      a.remove();
      window.URL.revokeObjectURL(url);
      responseBox.textContent = "Download started!";
      errorBox.textContent = "";
    })
    .catch(err => {
      errorBox.textContent = err.message;
      responseBox.textContent = "";
    });
}

//list all files
function listFiles() {
  const listElement = document.getElementById("fileList");
  listElement.innerHTML = "";

  fetch(`${API_URL}/list`)
    .then(res => res.json())
    .then(files => {
      if (files.length === 0) {
        listElement.innerHTML = "<li>No files found</li>";
        return;
      }
      files.forEach(file => {
        const li = document.createElement("li");
        li.textContent = file;
        listElement.appendChild(li);
      });
    })
    .catch(err => {
      listElement.innerHTML = "<li>Error fetching file list</li>";
    });
}
