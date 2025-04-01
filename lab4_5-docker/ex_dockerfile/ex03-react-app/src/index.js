import React from 'react';
import ReactDOM from 'react-dom/client'; // Import từ 'react-dom/client'
import App from './App';

const root = ReactDOM.createRoot(document.getElementById('root')); // Tạo root
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
