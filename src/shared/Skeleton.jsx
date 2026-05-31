import React from 'react';

/**
 * Modern glassmorphic loading skeleton component.
 */
export function Skeleton({ width = '100%', height = '20px', borderRadius = '4px', className = '', style = {}, ...props }) {
  const defaultStyle = {
    width,
    height,
    borderRadius,
    background: 'linear-gradient(90deg, rgba(255, 255, 255, 0.05) 25%, rgba(255, 255, 255, 0.12) 50%, rgba(255, 255, 255, 0.05) 75%)',
    backgroundSize: '200% 100%',
    animation: 'shimmer-slide 1.6s infinite linear',
    border: '1px solid rgba(255, 255, 255, 0.05)',
    boxShadow: '0 4px 12px rgba(0, 0, 0, 0.05)',
    ...style,
  };

  return (
    <div
      style={defaultStyle}
      className={`skeleton-loader ${className}`}
      {...props}
    />
  );
}

export function SkeletonEventCard() {
  return (
    <div style={{
      background: 'rgba(255, 255, 255, 0.03)',
      backdropFilter: 'blur(12px)',
      border: '1px solid var(--bdr)',
      borderRadius: '16px',
      padding: '20px',
      marginBottom: '20px',
      display: 'flex',
      flexDirection: 'column',
      gap: '12px'
    }}>
      <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
        <Skeleton width="30px" height="30px" borderRadius="50%" />
        <Skeleton width="180px" height="20px" />
      </div>
      <Skeleton width="120px" height="14px" />
      <Skeleton width="100%" height="45px" />
      <div style={{ display: 'flex', gap: '8px' }}>
        <Skeleton width="80px" height="24px" borderRadius="12px" />
        <Skeleton width="60px" height="24px" borderRadius="12px" />
      </div>
    </div>
  );
}
