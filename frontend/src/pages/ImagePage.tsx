import React, { useEffect, useState, ChangeEvent } from 'react';
import { uploadImage, fetchImage, fetchAllImages } from '../services/api';
import { useAuth } from '../contexts/AuthContext';
import { Navbar, ImageEditor } from '../components';
import { useNavigate } from 'react-router-dom';
import styles from './css/ImagePages.module.css';

const ImagePage: React.FC = () => {
  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [uploadMessage, setUploadMessage] = useState<string>('');
  const [images, setImages] = useState<string[]>([]);
  const [selectedImage, setSelectedImage] = useState<string>('');
  const [imageURL, setImageURL] = useState<string>('');
  const [error, setError] = useState<string>('');
  const [isEditing, setIsEditing] = useState<boolean>(false);
  const [imageBlob, setImageBlob] = useState<Blob | null>(null);
  const { user, isPractitioner } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (!user) {
      navigate('/login');
      return;
    } if (!isPractitioner) {
      navigate('/login');
      return;
    }
    
    const fetchImages = async () => {
      try {
        const fetchedImages = await fetchAllImages();
        setImages(fetchedImages);
    
        if (fetchedImages.length > 0) {
          setSelectedImage(fetchedImages[0]);
        }
      } catch (err: any) {
        setError(err.message || 'Kunde inte hämta bildlistan.');
      } finally {
        setLoading(false);
      }
    };

    fetchImages();
  }, [user]);

  const handleFileChange = (e: ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files.length > 0) {
      setSelectedFile(e.target.files[0]);
    }
  };

  const handleUpload = async () => {
    if (!selectedFile) {
      setUploadMessage('Ingen fil vald.');
      return;
    }

    const formData = new FormData();
    formData.append('image', selectedFile);

    try {
      const response = await uploadImage(formData);
      const { filename } = response;
      setImages((prevImages) => [...prevImages, filename]);         
      setUploadMessage('Bild uppladdad framgångsrikt!');
    } catch (err: any) {
      setUploadMessage(err.message || 'Uppladdning misslyckades.');
    }
  };

  const handleDownload = async () => {
    if (!selectedImage) {
      setError('Ange ett filnamn för nedladdning.');
      return;
    }

    try {
      const blob = await fetchImage(selectedImage);
      const url = URL.createObjectURL(blob);
      setImageURL(url);
      setError('');
    } catch (err: any) {
      setError(err.message || 'Nedladdning misslyckades.');
      setImageURL('');
    }
  };

  const startEditing = async () => {
    if (!selectedImage) {
      setError('Välj en bild att redigera.');
      return;
    }
  
    try {
      const blob = await fetchImage(selectedImage);
      setImageBlob(blob);
      setIsEditing(true);
    } catch (err: any) {
      setError(err.message || 'Kunde inte ladda bilden för redigering.');
    }
  };

  const stopEditing = () => {
    setIsEditing(false);
  };

  return (
    <div>
      <Navbar />
      {isEditing && imageBlob && (
        <ImageEditor
          imageBlob={imageBlob} 
          onSave={(editedBlob) => {
            const formData = new FormData();
            formData.append('image', editedBlob);
          
            uploadImage(formData)
              .then((response) => {
                const { filename } = response;
                setImages((prevImages) => [...prevImages, filename]);
                setIsEditing(false);
                alert('Redigerad bild sparades!');
              })
              .catch((err) => {
                console.error('Uppladdning misslyckades', err);
                alert('Uppladdning misslyckades!');
              });
          }}
          onCancel={stopEditing}
        />
      )}
      <h2>Uppladdning av bild</h2>
      <input type="file" onChange={handleFileChange} />
      <button onClick={handleUpload}>Ladda upp bild</button>
      {uploadMessage && <p>{uploadMessage}</p>}

      <h2>Nedladdning av bild</h2>
      {loading ? (
        <p>Laddar bilder...</p>
      ) : images.length > 0 ? (
        <div className="styles.pickwheelContainer">
          <select
            className="styles.pickwheel"
            value={selectedImage}
            onChange={(e) => setSelectedImage(e.target.value)}
          >
            {images.map((image, index) => (
              <option key={index} value={image}>
                {image}
              </option>
            ))}
          </select>
          <button onClick={handleDownload}>Hämta bild</button>
          <button onClick={startEditing}>Redigera bild</button>
        </div>
      ) : (
        <p style={{ color: 'red' }}>Inga bilder hittades.</p>
      )}
      {imageURL && (
        <div>
          <h3>Hämtad bild:</h3>
          <img src={imageURL} alt="Nedladdad" style={{ maxWidth: '100%', height: 'auto' }} />
        </div>
      )}
    </div>
  );
};

export default ImagePage;