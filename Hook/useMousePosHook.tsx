import { useEffect, useRef } from "react";

interface TrackingData {
  userId: string;
  mousePosX: number;
  mousePosY: number;
  regTime: string;
}

export function useMouseTracker(userId: string, intervalMs = 1000) {
  const mousePosRef = useRef({ x: 0, y: 0 });

  useEffect(() => {
    const handleMouseMove = (event: MouseEvent) => {
      mousePosRef.current = { x: event.clientX, y: event.clientY };
    };

    window.addEventListener("mousemove", handleMouseMove);

    const interval = setInterval(() => {
      sendMouseData(mousePosRef.current);
    }, intervalMs);

    return () => {
      window.removeEventListener("mousemove", handleMouseMove);
      clearInterval(interval);
    };
    // Only depend on intervalMs and userId
  }, [intervalMs, userId]);

  const sendMouseData = async (pos: { x: number; y: number }) => {
    const data: TrackingData = {
      userId,
      mousePosX: pos.x,
      mousePosY: pos.y,
      regTime: new Date().toISOString(),
    };

    try {
      await fetch("http://localhost:8080/api/mousePosition", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      });
    } catch (error) {
      console.error("Failed to send mouse data:", error);
    }
  };
}
