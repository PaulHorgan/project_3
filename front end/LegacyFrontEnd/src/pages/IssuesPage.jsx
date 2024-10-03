import React from 'react';
import { useState, useEffect } from 'react';
import "../CSS/Issues.css";


function IssuesPage() {
  // State for issues and new issue form fields
  const [issues, setIssues] = useState([]);
  const [newIssue, setNewIssue] = useState({
    description: '',
    location: '',
    status: 'logged'
  });
  const [errors, setErrors] = useState({});

  // Fetch issues on component mount
  useEffect(() => {
    fetchIssues();
  }, []);

  const fetchIssues = () => {
    fetch('http://localhost:8085/Issues/getAll')
      .then(response => response.json())
      .then(data => setIssues(data))
      .catch(error => console.error("Failed to fetch issues:", error));
  };

  // Form validation
  const validateFields = () => {
    const newErrors = {};
    if (!newIssue.description) newErrors.description = "Description is required";
    if (!newIssue.location) newErrors.location = "Location is required";
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  // Handle form submission for adding a new issue
  const handleAddNewIssue = (e) => {
    e.preventDefault();
    if (!validateFields()) return;

    fetch('http://localhost:8085/Issues/new', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newIssue),
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Failed to add new issue');
        }
        return response.json();
      })
      .then(savedIssue => {
        setIssues([...issues, savedIssue]); // Add new issue to state
        setNewIssue({ description: '', location: '', status: 'logged' }); // Reset form
      })
      .catch(error => {
        console.error("Failed to add new issue:", error);
        setErrors({ submit: 'Failed to add new issue. Please try again.' });
      });
  };

  // Handle input changes for the form
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewIssue({ ...newIssue, [name]: value });
  };

  return (
    <div className='body'>
      <div className='filler'></div>
{/* Form to add new issue */}
<div className="add-issue-form">
        <h2>Add New Issue - Please check if your issue is already known below </h2>
        <form onSubmit={handleAddNewIssue}>
          <div>
            <label>Description:</label>
            <input
              type="text"
              name="description"
              value={newIssue.description}
              onChange={handleInputChange}
              style={{ borderColor: errors.description ? 'red' : '' }}
            />
            {errors.description && <span style={{ color: 'red' }}>{errors.description}</span>}
          </div>
          <div>
            <label>Location:</label>
            <input
              type="text"
              name="location"
              value={newIssue.location}
              onChange={handleInputChange}
              style={{ borderColor: errors.location ? 'red' : '' }}
            />
            {errors.location && <span style={{ color: 'red' }}>{errors.location}</span>}
          </div>
          <div>
            <label>Status:</label>
            <input
              type="text"
              name="status"
              value={newIssue.status}
              onChange={handleInputChange}
              readOnly
            />
          </div>
          {errors.submit && <span style={{ color: 'red' }}>{errors.submit}</span>}
          <button type="submit">Add Issue</button>
        </form>
      </div>

      <h1>Issue Tracker</h1>

      {/* Table to display issues */}
      <div className="issues-table">
        <h2>Current Issues</h2>
        <table>
          <thead>
            <tr>
              <th>Issue ID</th>
              <th>Issue Description</th>
              <th>Issue Location</th>
              <th>Issue Status</th>
            </tr>
          </thead>
          <tbody>
            {issues.length > 0 ? (
              issues.map((issue, index) => (
                <tr key={issue.id || index}>
                  <td>{issue.id}</td>
                  <td>{issue.description}</td>
                  <td>{issue.location}</td>
                  <td>{issue.status}</td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="4">No issues found</td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      
    </div>
  );
};


export default IssuesPage;