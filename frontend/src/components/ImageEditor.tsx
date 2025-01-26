import React, { useRef, useState, useEffect } from 'react';

interface ImageEditorProps {
  imageBlob: Blob; 
  onSave: (editedImage: Blob) => void;
  onCancel: () => void; 
}

export const ImageEditor: React.FC<ImageEditorProps> = ({ imageBlob, onSave, onCancel }) => {
  const canvasRef = useRef<HTMLCanvasElement | null>(null);
  const [text, setText] = useState<string>('');
  const [drawing, setDrawing] = useState<boolean>(false);
  const [mousePosition, setMousePosition] = useState<{ x: number; y: number } | null>(null);

  useEffect(() => {
    const loadImage = () => {
      const canvas = canvasRef.current;
      const ctx = canvas?.getContext('2d');
      const img = new Image();
      img.src = URL.createObjectURL(imageBlob); 
      img.onload = () => {
        if (canvas && ctx) {
          ctx.clearRect(0, 0, canvas.width, canvas.height);
          ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
        }
      };
    };

    loadImage();
  }, [imageBlob]);

  const handleAddText = () => {
    const canvas = canvasRef.current;
    const ctx = canvas?.getContext('2d');
    if (canvas && ctx) {
      ctx.font = '20px Arial';
      ctx.fillStyle = 'black';
      ctx.fillText(text, canvas.width / 2, canvas.height / 2); 
    }
  };

  const handleMouseDown = (event: React.MouseEvent<HTMLCanvasElement>) => {
    setDrawing(true);
    setMousePosition({ x: event.nativeEvent.offsetX, y: event.nativeEvent.offsetY });
  };

  const handleMouseUp = () => {
    setDrawing(false);
    setMousePosition(null);
  };

  const handleMouseMove = (event: React.MouseEvent<HTMLCanvasElement>) => {
    if (!drawing) return;
    const canvas = canvasRef.current;
    const ctx = canvas?.getContext('2d');
    if (canvas && ctx && mousePosition) {
      const newMousePosition = { x: event.nativeEvent.offsetX, y: event.nativeEvent.offsetY };
      ctx.beginPath();
      ctx.moveTo(mousePosition.x, mousePosition.y);
      ctx.lineTo(newMousePosition.x, newMousePosition.y);
      ctx.strokeStyle = 'red';
      ctx.lineWidth = 2;
      ctx.stroke();
      setMousePosition(newMousePosition);
    }
  };

  const handleSave = () => {
    const canvas = canvasRef.current;
    if (canvas) {
      canvas.toBlob((blob) => {
        if (blob) {
          onSave(blob);
        }
      }, 'image/png');
    }
  };

  return (
    <div>
      <h2>Bildredigerare</h2>
      <input
        type="text"
        placeholder="Skriv text"
        value={text}
        onChange={(e) => setText(e.target.value)}
      />
      <button onClick={handleAddText}>Lägg till text</button>
      <button onClick={handleSave}>Spara bild</button>
      <button onClick={onCancel}>Avbryt</button>
      <canvas
        ref={canvasRef}
        width={800}
        height={600}
        style={{ border: '1px solid black' }}
        onMouseDown={handleMouseDown}
        onMouseUp={handleMouseUp}
        onMouseMove={handleMouseMove}
      ></canvas>
    </div>
  );
};