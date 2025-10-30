const express = require('express');
const path = require('path');
const app = express();
// Use port 3000 by default, or an environment variable (standard for CI/CD)
const PORT = process.env.PORT || 3000;

// Set the current directory as the static folder. 
// This allows the server to find and serve the index.html file.
app.use(express.static(__dirname));

// Define the root route to explicitly serve the index.html file
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'index.html'));
});

// Start the server
app.listen(PORT, () => {
    // Log the running port, which is crucial for CI/CD job logs
    console.log(`Attendance App Server listening on port ${PORT}`);
    console.log(`Access the application at http://localhost:${PORT}`);
});