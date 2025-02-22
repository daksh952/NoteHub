import React, { useState, useEffect } from "react";
import { BookOpen, Download, FileText, LogOut, Search ,User} from "lucide-react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const AllNotes = () => {
  const [notes, setNotes] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const navigate = useNavigate();
  const [teachers, setTeachers] = useState([]);
  const [selectedTeacher, setSelectedTeacher] = useState("");
  const [teacherName, setTeacherName] = useState("");

  useEffect(() => {
      const storedName = localStorage.getItem("teacherName");
      if (storedName) {
        setTeacherName(storedName);
      }
    }, []);
  // Fetch all notes when component mounts
  useEffect(() => {
    fetchNotes();
  }, []);

  const fetchNotes = async () => {
    try {
      const response = await axios.get("http://localhost:8080/teacher/allnotes");
  
      // Ensure the date is formatted as YYYY-MM-DD (same as TeacherDashboard)
      const formattedNotes = response.data.map((note) => ({
        ...note,
        uploadedAt: note.uploadedAt ? new Date(note.uploadedAt).toLocaleDateString() : "No Date",
      }));
  
      setNotes(formattedNotes);
  
      // Extract unique teachers
      const uniqueTeachers = [...new Set(response.data.map(note => note.teacherName))];
      setTeachers(uniqueTeachers);
  
    } catch (error) {
      console.error("Error fetching notes:", error);
      alert("Failed to load notes.");
    }
  };

  const handleDownload = async (noteId, fileName) => {
    try {
      const response = await axios.get(`http://localhost:8080/teacher/download/${noteId}`, {
        responseType: "blob",
      });

      const blob = new Blob([response.data], { type: "application/pdf" });
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", fileName.endsWith(".pdf") ? fileName : `${fileName}.pdf`);
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    } catch (error) {
      console.error("Error downloading file:", error);
      alert("Failed to download file.");
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("teacherId");
    navigate("/"); // Redirect to home/login page
  };

  const filteredNotes = notes.filter(
    (note) =>
      (selectedTeacher ? note.teacherName === selectedTeacher : true) && // Filter by teacher
      (
        (note.fileName && note.fileName.toLowerCase().includes(searchTerm.toLowerCase())) ||
        (note.subject && note.subject.toLowerCase().includes(searchTerm.toLowerCase())) ||
        (note.teacherName && note.teacherName.toLowerCase().includes(searchTerm.toLowerCase()))
      )
  );

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Sidebar */}
      <div className="fixed w-64 pt-20 pt-20 h-full bg-white shadow-lg">
              {/* Profile Section */}
              <div className="flex flex-col items-center justify-center h-20 border-b">
                <h1 className="text-xl font-bold text-indigo-600">Teacher Portal</h1>
                <div className="flex items-center mt-2 space-x-2">
                  
                  <div className="h-8 w-8 flex items-center justify-center rounded-full bg-gray-300 text-gray-700">
                    <User className="h-5 w-5" />
                  </div>
                  <p className="text-gray-700">{localStorage.getItem("teacherName") || "Teacher"}</p>
                </div>
              </div>
        <nav className="mt-6">
          <div
            className="px-6 py-4 text-gray-500 hover:bg-indigo-50 hover:text-indigo-600 cursor-pointer flex items-center"
            onClick={() => navigate("/teacher")}
          >
            <FileText className="h-5 w-5 mr-3" />
            My Notes
          </div>
          <div
            className="px-6 py-4 text-gray-500 hover:bg-indigo-50 hover:text-indigo-600 cursor-pointer flex items-center"
            onClick={() => navigate("/teacher/allnotes")}
          >
            <FileText className="h-5 w-5 mr-3" />
            All Notes
          </div>
          <div
            className="px-6 py-4 text-gray-500 hover:bg-indigo-50 hover:text-indigo-600 cursor-pointer flex items-center"
            onClick={() => navigate("/teacher/announcements")}
          >
            <FileText className="h-5 w-5 mr-3" />
            Announcements
          </div>
          <div className="px-6 py-4 text-gray-500 hover:bg-indigo-50 cursor-pointer flex items-center"
              onClick={() => navigate("/teacher/analytics")}>
              <FileText className="h-5 w-5 mr-3" />
              Analytics
          </div>
          <div
            className="px-6 py-4 text-gray-500 hover:bg-indigo-50 hover:text-indigo-600 cursor-pointer flex items-center"
            onClick={handleLogout}
          >
            <LogOut className="h-5 w-5 mr-3" />
            Logout
          </div>
        </nav>
      </div>

      {/* Main Content */}
      <div className="ml-64 pt-20 p-8">
        <h2 className="text-2xl font-bold text-gray-800 mb-6">All Notes</h2>

        {/* Search Bar */}
        <div className="relative mb-6">
          <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 h-5 w-5" />
          <input
            type="text"
            placeholder="Search notes by title, subject, or teacher..."
            className="w-full pl-10 pr-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>

        {/* Notes Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {filteredNotes.length > 0 ? (
            filteredNotes.map((note) => (
              <div key={note.id} className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-shadow">
                <div className="flex items-center justify-between mb-4">
                  <BookOpen className="h-8 w-8 text-blue-600" />
                  <span className="text-sm text-gray-500">{note.uploadedAt || "No Date"}</span>
                </div>
                <h3 className="text-lg font-semibold mb-2">{note.fileName}</h3>
                <p className="text-gray-600 mb-2">{note.subject}</p>
                <p className="text-gray-500 text-sm mb-4">By {note.teacherName}</p>
                <div className="flex justify-between items-center">
                  <button
                    className="flex items-center text-blue-600 hover:text-blue-800"
                    onClick={() => handleDownload(note.id, note.fileName)}
                  >
                    <Download className="h-4 w-4 mr-1" />
                    Download
                  </button>
                </div>
              </div>
            ))
          ) : (
            <p className="text-gray-500">No notes available.</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default AllNotes;
